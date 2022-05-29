package net.reikeb.arcanecraft.spell;

import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.SpellInit;

import javax.annotation.Nullable;
import javax.print.attribute.Attribute;
import java.util.Map;

public class Spell extends ForgeRegistryEntry<Spell> {

    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();
    private final ChatFormatting textFormatting;
    @Nullable
    private String descriptionId;

    public Spell(ChatFormatting textFormatting) {
        this.textFormatting = textFormatting;
    }

    public boolean isInstantenous() {
        return false;
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("spell", SpellInit.SPELLS_REGISTRY.get().getKey(this));
        }

        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getDisplayName() {
        return new TranslatableComponent(this.getDescriptionId());
    }

    public ChatFormatting getFormatting() {
        return this.textFormatting;
    }

    @Nullable
    public static Spell byId(String id) {
        return SpellInit.SPELLS_REGISTRY.get().getValue(ArcaneCraft.RL(id));
    }

    public static String getId(Spell spell) {
        return SpellInit.SPELLS_REGISTRY.get().toString();
    }
}