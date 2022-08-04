package sn.ept.git.seminaire.cicd.utils;

import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.models.BaseEntity;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author ISENE
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
        super();
    }

    public static  void presentOrThrow(Optional<?> optional, String template, String ... params){
        if(!optional.isPresent()){
            throw new ItemNotFoundException(
                    ItemNotFoundException.format(template, params)
            );
        }
    }

    public static  void absentOrThrow(Optional<?> optional, String template, String ... params){
        if(optional.isPresent()){
            throw new ItemExistsException(
                    ItemExistsException.format(template, params)
            );
        }
    }


    public static  void ThrowNotFound(String template, String ... params){
            throw new ItemNotFoundException(
                    ItemNotFoundException.format(template, params)
            );
    }

}