package se.mickelus.tetra.module.improvement;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.gui.GuiColors;
import se.mickelus.tetra.module.schematic.SchematicRarity;

import net.minecraft.client.gui.toasts.IToast.Visibility;

public class HoneToast implements IToast {
    private static final ResourceLocation texture = new ResourceLocation(TetraMod.MOD_ID,"textures/gui/toasts.png");

    private boolean hasPlayedSound = false;
    private ItemStack itemStack;

    public HoneToast(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public Visibility render(MatrixStack matrixStack, ToastGui toastGui, long delta) {
        if (itemStack != null) {
            toastGui.getMinecraft().getTextureManager().bind(texture);
            RenderSystem.color3f(1.0F, 1.0F, 1.0F);
            toastGui.blit(matrixStack, 0, 0, 0, 0, 160, 32);

            String itemName = toastGui.getMinecraft().font.plainSubstrByWidth(itemStack.getHoverName().getString(), 125);
            toastGui.getMinecraft().font.draw(matrixStack, I18n.get("tetra.hone.available"), 30, 7, SchematicRarity.hone.tint);
            toastGui.getMinecraft().font.draw(matrixStack, itemName, 30, 18, GuiColors.muted);

            if (!this.hasPlayedSound && delta > 0L) {
                this.hasPlayedSound = true;
                toastGui.getMinecraft().getSoundManager()
                        .play(SimpleSound.forUI(SoundEvents.PLAYER_LEVELUP, 0.6F, 0.7F));
            }

            RenderHelper.turnBackOn();
            toastGui.getMinecraft().getItemRenderer().renderAndDecorateItem(null, itemStack, 8, 8);

            return delta > 5000 ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        }

        return IToast.Visibility.HIDE;
    }
}
