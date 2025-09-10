package com.miniproject2.mysalon.entity;

import com.miniproject2.mysalon.entity.Category;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum CategoryLow {
    // TOP
    SHORT_SLEEVE(Category.TOP),
    LONG_SLEEVE(Category.TOP),
    SHIRT_BLOUSE(Category.TOP),
    KNIT_SWEATER(Category.TOP),
    SWEATSHIRT_HOODIE(Category.TOP),
    OTHER(Category.TOP),

    // OUTERWEAR
    CARDIGAN(Category.OUTERWEAR),
    JACKET(Category.OUTERWEAR),
    COAT(Category.OUTERWEAR),

    // DRESS/SKIRT
    MINI(Category.DRESS_SKIRT),
    MIDI(Category.DRESS_SKIRT),
    LONG(Category.DRESS_SKIRT),

    // ACCESSORIES/BAG
    BAG(Category.ACC_BAG),
    HAT(Category.ACC_BAG),
    ACCESSORY(Category.ACC_BAG),

    // LOUNGEWEAR/UNDERWEAR
    PAJAMAS(Category.LOUNGEWEAR_UNDERWEAR),
    UNDERWEAR(Category.LOUNGEWEAR_UNDERWEAR),

    // KIDS
    TOPS(Category.KIDS),
    BOTTOMS(Category.KIDS),

    // ALL
    ALL(Category.ALL);

    private final Category parent;

    CategoryLow(Category parent) {
        this.parent = parent;
    }

    public Category getParent() {
        return parent;
    }

    /**
     * 특정 상위 카테고리에 해당하는 모든 하위 카테고리 반환
     */
    public static List<CategoryLow> getByParent(Category parent) {
        return Arrays.stream(values())
                .filter(c -> c != ALL) // ALL 자체는 제외
                .filter(c -> c.getParent() == parent)
                .collect(Collectors.toList());
    }

    /**
     * ALL 선택 시 전체 카테고리 반환
     */
    public static List<CategoryLow> resolve(CategoryLow categoryLow) {
        if (categoryLow == ALL) {
            return Arrays.stream(values())
                    .filter(c -> c != ALL) // 자기 자신 제외
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(categoryLow);
    }
}
