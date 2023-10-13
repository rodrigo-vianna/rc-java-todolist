package br.com.rodrigovianna.todolist.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  public static void copyNonNullProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, Utils.getNullPropertyNames(src));
  }

  public static String[] getNullPropertyNames(Object source) {
    final var src = new BeanWrapperImpl(source);
    final var pds = src.getPropertyDescriptors();

    final Set<String> emptyNames = new HashSet<String>();
    for (final var pd : pds) {
      final var srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null)
        emptyNames.add(pd.getName());
    }
    final var result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
}
