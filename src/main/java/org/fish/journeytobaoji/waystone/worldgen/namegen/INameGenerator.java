package org.fish.journeytobaoji.waystone.worldgen.namegen;

import javax.annotation.Nullable;
import java.util.Random;

public interface INameGenerator {
    @Nullable
    String randomName(Random rand);
}