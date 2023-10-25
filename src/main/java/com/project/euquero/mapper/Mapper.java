package com.project.euquero.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination){
        List<D> destinationObject = new ArrayList<>();

        origin.forEach(x -> destinationObject.add(mapper.map(x, destination)));

        return destinationObject;
    }
}
