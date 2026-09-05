package net.nyrader.noendermen.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.nyrader.noendermen.config.ClientConfig;
import net.nyrader.noendermen.config.ServerConfig;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class FossilizedEnderBlock extends Block
{
    public FossilizedEnderBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public int getExpDrop(@NotNull BlockState state, LevelAccessor level, @NotNull BlockPos pos,
                          @Nullable BlockEntity blockEntity,
                          @Nullable Entity breaker,
                          @NotNull ItemStack tool)
    {

        return level.getRandom().nextIntBetweenInclusive(2, 5);
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

        DustParticleOptions particle = getParticleSettings();

        for(Direction direction : Direction.values())
        {
            BlockPos blockpos = pPos.relative(direction);
            if (!pLevel.getBlockState(blockpos).isSolidRender(pLevel, blockpos))
            {
                Direction.Axis direction$axis = direction.getAxis();
                double xAxis = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double yAxis = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double zAxis = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                pLevel.addParticle(particle, (double)pPos.getX() + xAxis, (double)pPos.getY() + yAxis, (double)pPos.getZ() + zAxis, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private static @NotNull DustParticleOptions getParticleSettings() {
        Vector3f color;
        if (ClientConfig.particleColorList.size() == 3)
        {
            color = new Vector3f(
                    ClientConfig.particleColorList.get(0).floatValue(),
                    ClientConfig.particleColorList.get(1).floatValue(),
                    ClientConfig.particleColorList.get(2).floatValue());
        }
        else
        {
            color = new Vector3f(0.4f, 1.0f, 0.75f);
        }

        float scale = ClientConfig.particleScale;
        return new DustParticleOptions(color, scale);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos,
                                                 @NotNull BlockState state, @NotNull Player player)
    {
        if (!level.isClientSide)
        {
            ItemStack tool = player.getMainHandItem();

            boolean hasSilkTouch = tool.getEnchantmentLevel(
                    level.registryAccess()
                            .lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(Enchantments.SILK_TOUCH)
            ) > 0;

            if (!hasSilkTouch && level.random.nextFloat() < ServerConfig.teleportChance)
            {
                tryTeleport(level, pos, state);
                return state;
            }
        }

        super.playerWillDestroy(level, pos, state, player);
        return state;
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
    public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos)
    {
        if (!ServerConfig.enableBlockLighting) { return 0; }
        return ServerConfig.blockLightLevel;
    }
}
