package org.fish.journeytobaoji.waystone.block;

import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.container.WaystoneSelectionContainer;
import net.blay09.mods.waystones.core.*;
import net.blay09.mods.waystones.tileentity.PortstoneTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class PortstoneBlock extends WaystoneBlockBase {

    private static final VoxelShape[] LOWER_SHAPES = new VoxelShape[]{
            // South
            VoxelShapes.or(
                    makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
                    makeCuboidShape(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
                    makeCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
                    makeCuboidShape(3.0, 9.0, 3.0, 13.0, 16.0, 7.0),
                    makeCuboidShape(4.0, 9.0, 7.0, 12.0, 16.0, 10.0),
                    makeCuboidShape(4.0, 9.0, 10.0, 12.0, 12.0, 12.0)
            ).simplify(),
            // West
            VoxelShapes.or(
                    makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
                    makeCuboidShape(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
                    makeCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
                    makeCuboidShape(9.0, 9.0, 3.0, 13.0, 16.0, 13.0),
                    makeCuboidShape(6.0, 9.0, 4.0, 9.0, 16.0, 12.0),
                    makeCuboidShape(4.0, 9.0, 4.0, 6.0, 12.0, 12.0)
            ).simplify(),
            // North
            VoxelShapes.or(
                    makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
                    makeCuboidShape(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
                    makeCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
                    makeCuboidShape(3.0, 9.0, 9.0, 13.0, 16.0, 13.0),
                    makeCuboidShape(4.0, 9.0, 6.0, 12.0, 16.0, 9.0),
                    makeCuboidShape(4.0, 9.0, 4.0, 12.0, 12.0, 6.0)
            ).simplify(),
            // East
            VoxelShapes.or(
                    makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
                    makeCuboidShape(1.0, 3.0, 1.0, 15.0, 7.0, 15.0),
                    makeCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0),
                    makeCuboidShape(3.0, 9.0, 3.0, 7.0, 16.0, 13.0),
                    makeCuboidShape(7.0, 9.0, 4.0, 10.0, 16.0, 12.0),
                    makeCuboidShape(10.0, 9.0, 4.0, 12.0, 12.0, 12.0)
            ).simplify()
    };

    private static final VoxelShape[] UPPER_SHAPES = new VoxelShape[]{
            // South
            VoxelShapes.or(
                    makeCuboidShape(3.0, 0.0, 3.0, 13.0, 7.0, 7.0),
                    makeCuboidShape(4.0, 0.0, 7.0, 12.0, 2.0, 9.0)
            ).simplify(),
            // West
            VoxelShapes.or(
                    makeCuboidShape(9.0, 0.0, 3.0, 13.0, 7.0, 13.0),
                    makeCuboidShape(7.0, 0.0, 4.0, 9.0, 2.0, 12.0)
            ).simplify(),
            // North
            VoxelShapes.or(
                    makeCuboidShape(3.0, 0.0, 9.0, 13.0, 7.0, 13.0),
                    makeCuboidShape(4.0, 0.0, 7.0, 12.0, 2.0, 9.0)
            ).simplify(),
            // East
            VoxelShapes.or(
                    makeCuboidShape(3.0, 0.0, 3.0, 7.0, 7.0, 13.0),
                    makeCuboidShape(7.0, 0.0, 4.0, 9.0, 2.0, 12.0)
            ).simplify()
    };

    public PortstoneBlock() {
        setDefaultState(stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        return state.get(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPES[direction.getHorizontalIndex()] : LOWER_SHAPES[direction.getHorizontalIndex()];
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PortstoneTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            NetworkHooks.openGui(((ServerPlayerEntity) player), new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("block.waystones.portstone");
                }

                @Override
                public Container createMenu(int i, PlayerInventory menu, PlayerEntity player) {
                    final IWaystone portstone = new Waystone(WaystoneTypes.PORTSTONE, UUID.randomUUID(), world.getDimensionKey(), pos, false, null);
                    return WaystoneSelectionContainer.createWaystoneSelection(i, player, WarpMode.PORTSTONE_TO_WAYSTONE, portstone);
                }
            }, it -> {
                it.writeByte(WarpMode.PORTSTONE_TO_WAYSTONE.ordinal());
                it.writeBlockPos(pos);
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HALF);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        TranslationTextComponent component = new TranslationTextComponent("tooltip.waystones.portstone");
        component.mergeStyle(TextFormatting.GRAY);
        tooltip.add(component);

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
