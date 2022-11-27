package com.van.rps.support.utils;

import com.van.rps.model.PickElements;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RandomPicker {
    private static final Random random = new Random();

    public PickElements calculateRandomComputerPick() {
        int randomIndex = random.nextInt(3);
        return PickElements.values()[randomIndex];
    }
}
