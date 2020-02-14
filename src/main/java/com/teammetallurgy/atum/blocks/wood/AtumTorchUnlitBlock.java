package com.teammetallurgy.atum.blocks.wood;

import com.google.common.collect.Maps;
import com.teammetallurgy.atum.utils.Constants;
import com.teammetallurgy.atum.utils.StackHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class AtumTorchUnlitBlock extends AtumTorchBlock {
    public static List<Block> TORCHES = new ArrayList<>();
    private static final Map<Block, Block> UNLIT = Maps.newHashMap();
    private static final Map<Block, Block> LIT = Maps.newHashMap();

    public AtumTorchUnlitBlock() {
        super(0);
        for (Block torch : TORCHES) {
            UNLIT.put(torch, this);
            LIT.put(this, torch);
        }
    }

    public static Block getUnlitTorch(Block torch) {
        return UNLIT.get(torch);
    }

    private static Block getLitTorch(Block torch) {
        return LIT.get(torch);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        ItemStack heldStack = player.getHeldItem(hand);
        Block block = Block.getBlockFromItem(heldStack.getItem());
        if ((heldStack.getItem() instanceof FlintAndSteelItem || block.getLightValue(block.getDefaultState(), world, pos) > 0)) {
            if (heldStack.getItem().isDamageable()) {
                heldStack.damageItem(1, player, (p) -> p.sendBreakAnimation(hand));
            }
            world.setBlockState(pos, getLitTorch(this).getStateForPlacement(state, rayTraceResult.getFace(), state, world, pos, pos, hand)); //TODO test
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 2.5F, 1.0F);
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (Block.getBlockFromItem(event.getItemStack().getItem()) instanceof AtumTorchUnlitBlock && state.getBlock().getLightValue(state.getBlock().getDefaultState(), event.getWorld(), event.getPos()) > 0) {
            BlockPos pos = event.getPos();
            event.setCanceled(true); //Cancel placement
            event.getItemStack().shrink(1);
            StackHelper.giveItem(event.getEntityPlayer(), event.getHand(), new ItemStack(getLitTorch(Block.getBlockFromItem(event.getItemStack().getItem()))));
            event.getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 10.0F, 1.0F, false);
        }
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
    }
}