package net.nyrader.noendermen.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.nyrader.noendermen.config.ClientConfig;
import net.nyrader.noendermen.config.ServerConfig;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class FossilizedEnderBlock extends Block
{

    public FossilizedEnderBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader world, RandomSource randomSource, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? 1 + randomSource.nextInt(5) : 0;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random)
    {
        if (!ClientConfig.enableBlockParticles) { return; }

        Player player = Minecraft.getInstance().player;
        if (player == null) { return; }

        double maxDistance = ClientConfig.particleMaxDistance;

        if (player.distanceToSqr(
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5) > maxDistance * maxDistance)
        {
            return;
        }

        spawnParticles(level, pos);
    }

    private static void spawnParticles(Level pLevel, BlockPos pPos)
    {
        RandomSource randomsource = pLevel.random;

        Vector3f color = new Vector3f(
                ClientConfig.particleColorList.get(0).floatValue(),
                ClientConfig.particleColorList.get(1).floatValue(),
                ClientConfig.particleColorList.get(2).floatValue());

        float scale = ClientConfig.particleScale;
        DustParticleOptions greenParticle = new DustParticleOptions(color, scale);

        for(Direction direction : Direction.values())
        {
            BlockPos blockpos = pPos.relative(direction);
            if (!pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos))
            {
                Direction.Axis direction$axis = direction.getAxis();
                double xAxis = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double yAxis = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double zAxis = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                pLevel.addParticle(greenParticle, (double)pPos.getX() + xAxis, (double)pPos.getY() + yAxis, (double)pPos.getZ() + zAxis, 0.0D, 0.0D, 0.0D);
            }
        }
    }


    @Override
    public void playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide)
        {
            ItemStack tool = player.getMainHandItem();

            // Check if it has Silk Touch
            boolean hasSilkTouch = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0;

            if (!hasSilkTouch && level.random.nextFloat() < ServerConfig.teleportChance) {
                tryTeleport(level, pos, state);
                return; // stop normal break
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }


    private void tryTeleport(Level level, BlockPos pos, BlockState state)
    {
        if (!ServerConfig.enableTeleport)  { return; }

        RandomSource random = level.random;

        int radius = ServerConfig.teleportRadius;
        int height = ServerConfig.teleportHeight;

        for (int i = 0; i < ServerConfig.maxTeleportAttempts; i++)
        {
            int dy = random.nextIntBetweenInclusive(-height, height);
            int dx = random.nextIntBetweenInclusive(-radius, radius);
            int dz = random.nextIntBetweenInclusive(-radius, radius);

            BlockPos target = pos.offset(dx, dy, dz);

            BlockState targetState = level.getBlockState(target);
            BlockState belowState = level.getBlockState(target.below());

            if (targetState.isAir() &&
                    level.getFluidState(target).isEmpty() &&
                    belowState.isFaceSturdy(level, target.below(), Direction.UP))
            {

                level.setBlock(target, state, 3);
                level.removeBlock(pos, false);

                if (ServerConfig.playTeleportSound) {
                    level.playSound(
                            null,
                            pos,
                            net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT,
                            net.minecraft.sounds.SoundSource.BLOCKS,
                            1.0F,
                            1.0F);
                }

                break;
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos)
    {
        if (!ServerConfig.enableBlockLighting) { return 0; }
        return ServerConfig.blockLightLevel;
    }
}
