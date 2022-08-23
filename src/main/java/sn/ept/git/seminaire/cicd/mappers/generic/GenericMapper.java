package sn.ept.git.seminaire.cicd.mappers.generic;

import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E, D> {
    E asEntity(D d);

    D asDTO(E e);

    List<E> asEntityList(List<D> dList);
    List<D> asDTOList(List<E> eList);

}