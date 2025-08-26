package org.fish.journeytobaoji.waystone.client.gui.widget;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface ITooltipProvider {
    boolean shouldShowTooltip();
    List<ITextComponent> getTooltip();
}
