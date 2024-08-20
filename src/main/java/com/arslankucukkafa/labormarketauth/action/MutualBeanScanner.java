package com.arslankucukkafa.labormarketauth.action;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;

@Component
public class MutualBeanScanner {

    private final String willScanPackage = "com.arslankucukkafa";

    protected Set<String> findClassesFilterAnnotation(Class<? extends Annotation> annotationType) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));

        Set<String> annotatedClasses = new HashSet<>();

            Set<BeanDefinition> beanDefs = provider.findCandidateComponents(willScanPackage);
            beanDefs.stream()
                    .map(BeanDefinition::getBeanClassName)
                    .forEach(annotatedClasses::add);
        return annotatedClasses;
    }

    public Set<String> mutualAnnotationCluster(List<Class<? extends Annotation>> annotationTypes) {
        List<Set<String>> ret = new ArrayList<>();

        for (Class<? extends Annotation> annotationType : annotationTypes) {
            Set<String> annotatedClasses = findClassesFilterAnnotation(annotationType);
            ret.add(annotatedClasses);
        }
        var mutualSet = getMutualStringFromHashMap(ret);

        return mutualSet;
    }

    protected Set<String> getMutualStringFromHashMap (List<Set<String>> foundAnnotatedClasses) {
        if (foundAnnotatedClasses == null || foundAnnotatedClasses.isEmpty()) {
            return Collections.emptySet();
        }

        // İlk seti al ve ortak stringler kümesini bununla başlat
        Set<String> mutualSet = new HashSet<>(foundAnnotatedClasses.get(0));

        // Diğer setlerle kesişim (intersection) yap
        for (int i = 1; i < foundAnnotatedClasses.size(); i++) {
            mutualSet.retainAll(foundAnnotatedClasses.get(i));
            if (mutualSet.isEmpty()) {
                break;  // Eğer kesişim boşsa, daha fazla kontrol etmeye gerek yok
            }
        }

        return mutualSet;
    }
}
