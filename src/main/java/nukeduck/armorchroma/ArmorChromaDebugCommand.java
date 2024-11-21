package nukeduck.armorchroma;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nukeduck.armorchroma.config.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static com.mojang.brigadier.arguments.DoubleArgumentType.getDouble;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ArmorChromaDebugCommand {

    private static final DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(
            entityName -> Text.stringifiedTranslatable("commands.enchant.failed.itemless", entityName)
    );

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("armorchroma").then(
                literal("setarmor").then(
                        argument("points", doubleArg())
                                .executes(ArmorChromaDebugCommand::executeSetArmor)
                )
        ).then(
                literal("resetarmor")
                        .executes(ArmorChromaDebugCommand::executeResetArmor)
        ).then(
                literal("setglint").then(
                        argument("glint", bool())
                                .executes(ArmorChromaDebugCommand::executeSetGlint)
                )
        ));
    }


    private static int executeSetArmor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack stack = getActiveStack(context);
        double points = getDouble(context, "points");
        AttributeModifiersComponent modifiersComponent = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if (modifiersComponent == null) {
            modifiersComponent = new AttributeModifiersComponent(new ArrayList<>(1), true);
        } else {
            // Copying modifiersComponent.modifiers because it may be immutable
            List<AttributeModifiersComponent.Entry> modifiers = Util.filter(modifiersComponent.modifiers(), entry -> entry.attribute() != EntityAttributes.ARMOR);
            modifiersComponent = new AttributeModifiersComponent(modifiers, modifiersComponent.showInTooltip());
        }

        EntityAttributeModifier attributeModifier = new EntityAttributeModifier(Identifier.of(ArmorChroma.MODID, UUID.randomUUID().toString()), points, EntityAttributeModifier.Operation.ADD_VALUE);
        AttributeModifiersComponent.Entry entry = new AttributeModifiersComponent.Entry(EntityAttributes.ARMOR, attributeModifier, AttributeModifierSlot.ANY);
        modifiersComponent.modifiers().add(entry);
        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiersComponent);

        return Command.SINGLE_SUCCESS;
    }

    private static int executeResetArmor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack stack = getActiveStack(context);
        AttributeModifiersComponent oldComponent = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if (oldComponent != null) {
            List<AttributeModifiersComponent.Entry> modifiers = Util.filter(oldComponent.modifiers(),
                    entry -> entry.attribute() != EntityAttributes.ARMOR || !entry.modifier().id().getNamespace().equals(ArmorChroma.MODID));

            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, new AttributeModifiersComponent(modifiers, oldComponent.showInTooltip()));
        }

        return Command.SINGLE_SUCCESS;
    }

    private static int executeSetGlint(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack stack = getActiveStack(context);
        boolean glint = getBool(context, "glint");

        if (glint) {
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        } else {
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        }

        return Command.SINGLE_SUCCESS;
    }


    private static ItemStack getActiveStack(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        ItemStack stack = player.getMainHandStack();

        if (stack.isEmpty()) {
            throw FAILED_ITEMLESS_EXCEPTION.create(player.getName().getString());
        }

        return stack;
    }
}
