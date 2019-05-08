package com.ithellam.jamb.api.siren;

import java.util.UUID;
import com.ithellam.common.siren.CollectionSirenDto;
import com.ithellam.common.siren.SirenEntityPropertiesBase;
import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.jamb.api.siren.ContainerCollectionSirenDto.ContainerCollectionSirenDtoProperties;

public class ContainerCollectionSirenDto extends CollectionSirenDto<ContainerSirenDto, ContainerCollectionSirenDtoProperties> {
    public static class ContainerCollectionSirenDtoProperties extends SirenEntityPropertiesBase {

        @DeserializationConstructor
        private ContainerCollectionSirenDtoProperties() {
            this(null);
        }

        public ContainerCollectionSirenDtoProperties(final UUID id) {
            super(id);
        }
    }
}
