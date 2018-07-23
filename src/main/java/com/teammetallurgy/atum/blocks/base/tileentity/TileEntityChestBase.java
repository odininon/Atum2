package com.teammetallurgy.atum.blocks.base.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityChestBase extends TileEntityChest {
    public boolean canBeSingle;
    public boolean canBeDouble;

    public TileEntityChestBase(boolean canBeSingle, boolean canBeDouble) {
        this.canBeSingle = canBeSingle;
        this.canBeDouble = canBeDouble;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    private void setNeighbor(TileEntityChest chest, EnumFacing side) {
        if (chest.isInvalid()) {
            adjacentChestChecked = false;
        } else if (adjacentChestChecked) {
            switch (side) {
                case NORTH:
                    if (adjacentChestZNeg != chest) {
                        adjacentChestChecked = false;
                    }
                    break;
                case SOUTH:
                    if (adjacentChestZPos != chest) {
                        adjacentChestChecked = false;
                    }
                    break;
                case EAST:
                    if (adjacentChestXPos != chest) {
                        adjacentChestChecked = false;
                    }
                    break;
                case WEST:
                    if (adjacentChestXNeg != chest) {
                        adjacentChestChecked = false;
                    }
            }
        }
    }

    @Nullable
    @Override
    protected TileEntityChest getAdjacentChest(@Nonnull EnumFacing side) {
        BlockPos pos = this.pos.offset(side);

        if (isChestAt(pos)) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileEntityChestBase) {
                TileEntityChestBase chestBase = (TileEntityChestBase) tileEntity;
                chestBase.setNeighbor(this, side.getOpposite());
                return chestBase;
            }
        }
        return null;
    }

    private boolean isChestAt(BlockPos pos) {
        if (world == null) {
            return false;
        } else {
            Block block = world.getBlockState(pos).getBlock();
            TileEntity tileEntity = world.getTileEntity(pos);
            return block instanceof BlockChest && ((BlockChest) block).chestType == getChestType() && tileEntity instanceof TileEntityChestBase && block == blockType;
        }
    }

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (doubleChestHandler == null || doubleChestHandler.needsRefresh()) {
                doubleChestHandler = VanillaDoubleChestItemHandler.get(this);
            }
            if (doubleChestHandler != null && doubleChestHandler != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
                return (T) doubleChestHandler;
            }
        }
        return super.getCapability(capability, facing);
    }
}