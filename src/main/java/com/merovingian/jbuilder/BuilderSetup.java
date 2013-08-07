/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder;

import com.google.common.base.Function;
import com.merovingian.jbuilder.persistance.PersistenceService;
import com.merovingian.jbuilder.util.ReflectionUtilImpl;
import com.merovingian.jbuilder.persistance.PersistenceServiceImpl;
import com.merovingian.jbuilder.propertynaming.PropertyNamer;
import com.merovingian.jbuilder.propertynaming.SequentialPropertyNamer;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jasonr
 */
public final class BuilderSetup {

    private static PersistenceService persistenceService;
    public static boolean AutoNameProperties;
    private static Map<Class<?>, PropertyNamer> propertyNamers;
    private static PropertyNamer defaultPropertyNamer;
    private static List<Member> disabledAutoNameProperties;
    public static boolean HasDisabledAutoNameProperties;

    private BuilderSetup() {
        ResetToDefaults();
    }

    public static void ResetToDefaults() {
        SetDefaultPropertyNamer(new SequentialPropertyNamer(new ReflectionUtilImpl()));
        persistenceService = new PersistenceServiceImpl();
        AutoNameProperties = true;
        propertyNamers = new HashMap<Class<?>, PropertyNamer>();
        HasDisabledAutoNameProperties = false;
        disabledAutoNameProperties = new ArrayList<Member>();
    }

    public static void SetDefaultPropertyNamer(PropertyNamer propertyNamer) {
        defaultPropertyNamer = propertyNamer;
    }

    public static void SetPersistenceService(PersistenceService service) {
        persistenceService = service;
    }

    public static PersistenceService GetPersistenceService() {
        return persistenceService;
    }

    public static <F, T> void SetCreatePersistenceMethod(Class<T> c, Function<F, T> saveMethod) {
        persistenceService.SetPersistenceCreateMethod(c, saveMethod);
    }

    public static <F, T> void SetUpdatePersistenceMethod(Class<T> c, Function<F, T> saveMethod) {
        persistenceService.SetPersistenceUpdateMethod(c, saveMethod);
    }

    public static void SetPropertyNamerFor(Class<?> c, PropertyNamer propertyNamer) {
        propertyNamers.put(c, propertyNamer);
    }

    public static PropertyNamer GetPropertyNamerFor(Class<?> c) {
        if (!propertyNamers.containsKey(c)) {
            return defaultPropertyNamer;
        }

        return propertyNamers.get(c);
    }

    public static void DisablePropertyNamingFor(Member member) {
        HasDisabledAutoNameProperties = true;
        disabledAutoNameProperties.add(member);
    }

    public static boolean ShouldIgnoreProperty(Member member) {
        for (Member disableMember : disabledAutoNameProperties) {
            if (disableMember.equals(member)) {
                return true;
            }
        }

        return false;
    }
//        private static <TModel, T> Member GetProperty(Expression<Func<TModel, T>> expression)
//        {
//            MemberExpression memberExpression = GetMemberExpression(expression);
//
//            return memberExpression.Member;
//        }
//
//        private static <TModel, T> MemberExpression GetMemberExpression(Expression<Func<TModel, T>> expression)
//        {
//            MemberExpression memberExpression = null;
//            if (expression.Body.NodeType == ExpressionType.MemberAccess)
//            {
//                memberExpression = expression.Body as MemberExpression;
//            }
//
//            return memberExpression;
//        }
}
