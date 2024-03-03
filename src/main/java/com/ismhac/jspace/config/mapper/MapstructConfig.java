package com.ismhac.jspace.config.mapper;

import org.mapstruct.*;

@MapperConfig(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface MapstructConfig {
}

