package sn.ept.git.seminaire.cicd.mappers.generic;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {
    E asEntity(D d);

    D asDTO(E e);

    default List<E> asEntityList(List<D> dList){
        return dList.stream().map(this::asEntity).collect(Collectors.toList());
    }

    default List<D> asDTOList(List<E> eList){
        return eList.stream().map(this::asDTO).collect(Collectors.toList());
    }

}