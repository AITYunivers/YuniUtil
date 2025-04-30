package io.github.yunivers.yuniutil.item;

import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class YuniTemplateTooltipItem extends TemplateItem implements CustomTooltipProvider
{
    private final Identifier identifier;
    private final int tooltipCount;

    public YuniTemplateTooltipItem(Identifier identifier, int tooltipCount)
    {
        super(identifier);
        this.identifier = identifier;
        this.tooltipCount = tooltipCount;
    }

    @Override
    public String[] getTooltip(ItemStack itemStack, String s)
    {
        TranslationStorage translatable = TranslationStorage.getInstance();

        ArrayList<String> list = new ArrayList<>();
        list.add(s);
        for (int i = 1; i <= tooltipCount; i++)
            list.add(translatable.get("item." + identifier.namespace + "." + identifier.getPath() + ".tooltip." + i));
        return list.toArray(new String[0]);
    }
}
