package com.ithellam.jamb.api.siren;

import java.util.UUID;
import com.ithellam.common.siren.CollectionSirenDto;
import com.ithellam.common.siren.SirenEntityPropertiesBase;
import com.ithellam.common.utils.DeserializationConstructor;
import com.ithellam.jamb.api.siren.ProductCollectionSirenDto.ProductCollectionSirenDtoProperties;

public class ProductCollectionSirenDto extends CollectionSirenDto<ProductSirenDto, ProductCollectionSirenDtoProperties> {
    public static class ProductCollectionSirenDtoProperties extends SirenEntityPropertiesBase {

        @DeserializationConstructor
        private ProductCollectionSirenDtoProperties() {
            this(null);
        }

        public ProductCollectionSirenDtoProperties(final UUID id) {
            super(id);
        }
    }
}
