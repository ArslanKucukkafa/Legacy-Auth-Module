package com.arslankucukkafa.labormarketauth;

import com.arslankucukkafa.labormarketauth.action.MutualBeanScanner;
import com.arslankucukkafa.labormarketauth.action.Permissionable;
import com.arslankucukkafa.labormarketauth.idm.controller.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class PermissionableTest {

    @Autowired
    MutualBeanScanner mutualBeanScanner;

    @Test
    public void test() {
        List<Class<? extends Annotation>> annotationTypes = List.of(RestController.class, Permissionable.class);
        Set<String> stringSet = mutualBeanScanner.mutualAnnotationCluster(annotationTypes);
        Assertions.assertEquals(1, stringSet.size());
        Assertions.assertTrue(stringSet.contains(AuthController.class.getName()));

    }

}

