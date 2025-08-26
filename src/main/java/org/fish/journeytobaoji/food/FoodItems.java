package org.fish.journeytobaoji.food;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.fish.journeytobaoji.main.JourneyToBaoji;

import java.util.function.Supplier;

public class FoodItems {

    public static final DeferredRegister<Item> FOODITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JourneyToBaoji.MODID+"-food");//modid:journeytobaoji-food

    public static void init() {
        FOODITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ItemGroup TAB_JOURNEYTOBAOJI_FOOD = new ItemGroup("JourneytobaojiTab") {
        @Override
        public ItemStack makeIcon() {
            return  new ItemStack(Items.APPLE);
        }
    };

    //注册物品
    public static final RegistryObject<Item> WHEAT_FLOUR = register("wheat_flour", TAB_JOURNEYTOBAOJI_FOOD);
    public static final RegistryObject<Item> BATTER = register("batter", TAB_JOURNEYTOBAOJI_FOOD);
    public static final RegistryObject<Item> CHINESE_SLOPPY_JOE = register("chinese_sloppy_joe", TAB_JOURNEYTOBAOJI_FOOD, ModFoods.CHINESE_SLIIPY_JOE);
    public static final RegistryObject<Item> DOUGH = register("dough", TAB_JOURNEYTOBAOJI_FOOD);
    public static final RegistryObject<Item> MINCED_PORK = register("minced_pork", TAB_JOURNEYTOBAOJI_FOOD);
    public static final RegistryObject<Item> PANCAKES = register("pancakes", TAB_JOURNEYTOBAOJI_FOOD, ModFoods.PANCAKES);
    public static final RegistryObject<Item> STIRRED_PASTE = register("stirred_paste", TAB_JOURNEYTOBAOJI_FOOD, ModFoods.STIRRED_PASTE);

    public static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return  FOODITEMS.register(name, item);
    }

    public static RegistryObject<Item> register(final String name, ItemGroup tab){
        return register(name, () -> new Item(new Item.Properties().tab(tab)));
    }
    // register food
    public static RegistryObject<Item> register(final String name, ItemGroup tab, Food foodsetting) {
        return register(name, () -> new Item(new Item.Properties().tab(tab).food(foodsetting)));
    }

}
