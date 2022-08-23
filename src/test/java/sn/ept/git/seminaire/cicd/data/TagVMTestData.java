package sn.ept.git.seminaire.cicd.data;


import sn.ept.git.seminaire.cicd.dto.vm.TagVM;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TagVMTestData extends TestData {

    public static TagVM defaultVM() {
        return TagVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .version(Default.version)
                .deleted(Default.deleted)
                .enabled(Default.enabled)
                .name(Default.name)
                .description(Default.description)
                .build();
    }

    public static List<TagVM> defaultVMList() {
        Random random = new Random();
        int length = random.nextInt(4) + 1;
        return Stream.iterate(0, (Integer i) -> i+1)
                .limit(length)
                .map(i -> TagVMTestData.defaultVM())
                .collect(Collectors.toList());
    }

    public static TagVM updatedVM() {
        return TagVM
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .version(Update.version)
                .deleted(Update.deleted)
                .enabled(Update.enabled)
                .name(Update.name)
                .description(Update.description)
                .build();
    }
}
