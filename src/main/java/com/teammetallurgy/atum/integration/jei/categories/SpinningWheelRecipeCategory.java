package com.teammetallurgy.atum.integration.jei.categories;

import com.teammetallurgy.atum.api.recipe.spinningwheel.SpinningWheelRecipe;
import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.integration.jei.JEIIntegration;
import com.teammetallurgy.atum.utils.AtumUtils;
import com.teammetallurgy.atum.utils.Constants;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.awt.*;

public class SpinningWheelRecipeCategory implements IRecipeCategory<SpinningWheelRecipe> {
    public static final ResourceLocation SPINNING_WHEEL_GUI = new ResourceLocation(Constants.MOD_ID, "textures/gui/spinning_wheel.png");
    private final IDrawableStatic background;
    private final IDrawable icon;

    public SpinningWheelRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(SPINNING_WHEEL_GUI, 0, -6, 68, 32);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(AtumBlocks.SPINNING_WHEEL));
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return JEIIntegration.SPINNING_WHEEL;
    }

    @Override
    @Nonnull
    public Class<? extends SpinningWheelRecipe> getRecipeClass() {
        return SpinningWheelRecipe.class;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return AtumUtils.format(Constants.MOD_ID + "." + getUid().getPath());
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(@Nonnull SpinningWheelRecipe recipe, @Nonnull IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull SpinningWheelRecipe recipe, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 10);
        guiItemStacks.init(1, false, 46, 10);
        guiItemStacks.set(ingredients);

        guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex == 1) {
                boolean showAdvanced = Minecraft.getInstance().gameSettings.advancedItemTooltips || Screen.hasShiftDown();
                if (showAdvanced) {
                    tooltip.add(TextFormatting.DARK_GRAY + AtumUtils.format("jei.tooltip.recipe.id", recipe.getRegistryName()));
                }
            }
        });
    }

    @Override
    public void draw(SpinningWheelRecipe recipe, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        mc.fontRenderer.drawString(AtumUtils.format("gui.atum.rotations", recipe.getRotations()), 25, 0, Color.gray.getRGB());
        mc.fontRenderer.drawString(AtumUtils.format("gui.atum.rotations", 3), 4, 0, Color.gray.getRGB());
    }
}