/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.propertynaming;

import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.BuilderSetup;
import com.merovingian.jbuilder.exceptions.TypeCreationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jasonr
 */
public abstract class AbstractPropertyNamer implements PropertyNamer {

    protected final ReflectionUtil ReflectionUtil;

    //protected const BindingFlags FLAGS = (BindingFlags.Public | BindingFlags.Instance);
    protected AbstractPropertyNamer(ReflectionUtil reflectionUtil) {
        this.ReflectionUtil = reflectionUtil;
    }

    @Override
    public abstract <T> void setValuesOfAllIn(List<T> objects)  throws InvocationTargetException, IllegalAccessException, TypeCreationException;

    @Override
    public <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {

        // NOTE: for now just trust in the setX bean pattern
        for (Method method : obj.getClass().getMethods()) {
            if (method.getName().startsWith("set")) {
                // Call SetMember
                SetMemberValue(method, obj);
            }
        }

        // And public fields?
        for (Field field : obj.getClass().getFields()) {
            // Call SetMember
            SetMemberValue(field, obj);
        }

    }

    protected static <T> Object GetCurrentValue(Field field, T obj) throws InvocationTargetException, IllegalAccessException {
        return field.get(obj);
    }

    protected static <T> Object GetCurrentValue(Method method, T obj) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj);
    }

    protected static Class<?> GetMemberType(Field field) {
       return field.getType();
    }
    
    protected static Class<?> GetMemberType(Method method) {
       return method.getReturnType();
    }

    protected <T> void SetValue(Field field, T obj, Object value) throws InvocationTargetException, IllegalAccessException {
        field.set(obj, value);
    }
    
    protected <T> void SetValue(Method method, T obj, Object value)  throws InvocationTargetException, IllegalAccessException {
        method.invoke(obj, value);
    }
    
    protected <T> void HandleUnknownType(Class memberClass, Member member , T obj) {
        
    }
    
    protected abstract short GetInt16(Member member);

    protected abstract int GetInt32(Member member);

    protected abstract long GetInt64(Member member);

//    protected abstract decimal GetDecimal(Member member);

    protected abstract float GetSingle(Member member);

    protected abstract double GetDouble(Member member);

//    protected abstract ushort GetUInt16(Member member);
//
//    protected abstract uint GetUInt32(Member member);
//
//    protected abstract ulong GetUInt64(Member member);
//
//    protected abstract sbyte GetSByte(Member member);

    protected abstract byte GetByte(Member member);

    protected abstract Date GetDateTime(Member member);

    protected abstract String GetString(Member member);

    protected abstract boolean GetBoolean(Member member);

    protected abstract char GetChar(Member member);

    protected abstract Enum GetEnum(Member member);

    protected abstract UUID GetGuid(Member member);
    
    protected boolean ShouldIgnore(Member member) {
        if (BuilderSetup.ShouldIgnoreProperty(member)) {
            return true;
        }

        return false;
    }
    
    protected <T> void SetMemberValue(Method method, T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
        
        Class<?> c = GetMemberType(method);

        if (BuilderSetup.HasDisabledAutoNameProperties && ShouldIgnore(method)) {
            return;
        }

        Object currentValue = GetCurrentValue(method, obj);

        if (!ReflectionUtil.IsDefaultValue(currentValue)) {
            return;
        }

        SetValue(method, obj, GetValue(method, obj, c));
        
    }
    
    protected <T> void SetMemberValue(Field field, T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
        
        Class<?> c = GetMemberType(field);

        if (BuilderSetup.HasDisabledAutoNameProperties && ShouldIgnore(field)) {
            return;
        }

        Object currentValue = GetCurrentValue(field, obj);

        if (!ReflectionUtil.IsDefaultValue(currentValue)) {
            return;
        }

        SetValue(field, obj, GetValue(field, obj, c));
        
    }
    
    protected <T> Object GetValue(Member member, T obj, Class<?> c) {

        Object value = null;
        
        if (short.class.equals(c)) {
            value =  GetInt16(member);
        } else if (int.class.equals(c)) {
            value = GetInt32(member);
        } else if (long.class.equals(c)) {
            value = GetInt64(member);
//        } else if (type == typeof(decimal)) {
//            value = GetDecimal(member);
        } else if (float.class.equals(c)) {
            value = GetSingle(member);
        } else if (double.class.equals(c)) {
            value = GetDouble(member);
//        } else if (type == typeof(ushort)) {
//            value = GetUInt16(member);
//        } else if (type == typeof(uint)) {
//            value = GetUInt32(member);
//        } else if (type == typeof(ulong)) {
//            value = GetUInt64(member);
        } else if (char.class.equals(c)) {
            value = GetChar(member);
        } else if (byte.class.equals(c)) {
            value = GetByte(member);
//        } else if (type == typeof(sbyte)) {
//            value = GetSByte(member);
        } else if (Date.class.equals(c)) {
            value = GetDateTime(member);
        } else if (String.class.equals(c)) {
            value = GetString(member);
        } else if (boolean.class.equals(c)) {
            value = GetBoolean(member);
        } else if (Enum.class.equals(c)) {
            value = GetEnum(member);
        } else if (UUID.class.equals(c)) {
            value = GetGuid(member);
        } else {
            HandleUnknownType(c, member, obj);
        }

        return value;
        
    }

    protected static Enum[] GetEnumValues(Class<? extends Enum> enumClass) {
        return enumClass.getEnumConstants();
    }
}
