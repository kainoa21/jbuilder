/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.propertynaming;

import com.merovingian.jbuilder.util.ReflectionUtil;
import com.merovingian.jbuilder.exceptions.TypeCreationException;
import static com.merovingian.jbuilder.propertynaming.AbstractPropertyNamer.GetEnumValues;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jasonr
 */
public class SequentialPropertyNamer extends AbstractPropertyNamer {
    
    private int _sequenceNumber;
    
    public SequentialPropertyNamer(ReflectionUtil reflectionUtil) {
        super(reflectionUtil);
    }
    
    @Override
    public <T> void setValuesOfAllIn(List<T> objects) throws InvocationTargetException, IllegalAccessException, TypeCreationException {

        if (objects.isEmpty()) {
            return;
        }

        _sequenceNumber = 1;

        Class c = objects.get(0).getClass();


        for (T obj : objects) {

            super.setValuesOf(obj);

            _sequenceNumber++;
        }
    }

    @Override
    public <T> void setValuesOf(T obj) throws InvocationTargetException, IllegalAccessException, TypeCreationException {
        super.setValuesOf(obj);
        _sequenceNumber = 1;

    }

    /// <summary>
    /// Gets the new sequence number taking into account a maximum value.
    /// 
    /// If the current sequence number is above the maximum value it will 
    /// reset it to one, and continue the sequence from there until the maximum 
    /// value is reached again.
    /// </summary>
    /// <param name="sequenceNumber">The sequence number.</param>
    /// <param name="maxValue">The max value.</param>
    /// <returns></returns>
    private static int GetNewSequenceNumber(int sequenceNumber, int maxValue) {
        int newSequenceNumber = sequenceNumber % maxValue;
        
        if (newSequenceNumber == 0) {
            newSequenceNumber = maxValue;
        }

        return newSequenceNumber;
    }

    protected short GetInt16(Member member) {
        int newSequenceNumber = GetNewSequenceNumber(_sequenceNumber, Short.MAX_VALUE);
        return (short)newSequenceNumber;
    }

    protected int GetInt32(Member member) {
        return _sequenceNumber;
    }

    protected long GetInt64(Member member) {
        return (long)_sequenceNumber;
    }

//    protected decimal GetDecimal(Member member) {
//        return Convert.ToDecimal(_sequenceNumber);
//    }

    protected float GetSingle(Member member) {
        return (float)_sequenceNumber;
    }

    protected double GetDouble(Member member) {
        return (double)_sequenceNumber;
    }

//    protected ushort GetUInt16(Member member) {
//        return Convert.ToUInt16(_sequenceNumber);
//    }
//
//    protected uint GetUInt32(Member member) {
//        return Convert.ToUInt32(_sequenceNumber);
//    }
//
//    protected ulong GetUInt64(Member member) {
//        return Convert.ToUInt64(_sequenceNumber);
//    }

    protected char GetChar(Member member) {
        int newSequenceNumber = GetNewSequenceNumber(_sequenceNumber, 26);
        newSequenceNumber += 64;

        return (char)newSequenceNumber;
    }

    protected byte GetByte(Member member) {
        int newSequenceNumber = GetNewSequenceNumber(_sequenceNumber, Byte.MAX_VALUE);

        return (byte)newSequenceNumber;
    }

//    protected sbyte GetSByte(Member member) {
//        int newSequenceNumber = GetNewSequenceNumber(_sequenceNumber, sbyte.MaxValue);
//
//        return Convert.ToSByte(newSequenceNumber);
//    }

    protected Date GetDateTime(Member member) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, _sequenceNumber - 1);
        return now.getTime();
    }
    
    protected String GetString(Member member) {
        return member.getName() + _sequenceNumber;
    }

    protected boolean GetBoolean(Member member) {
        return (_sequenceNumber % 2) == 0 ? true : false;
    }

    protected Enum GetEnum(Member member) {
        
        Class enumClass = member.getClass();
        if (enumClass.isEnum()) {
            Enum[] enumValues = GetEnumValues(enumClass);
            int newSequenceNumber = GetNewSequenceNumber(_sequenceNumber, enumValues.length);
            return enumValues[newSequenceNumber];
        }

        return null;

    }

    protected UUID GetGuid(Member member) {
        
        // Java UUID is 16 Bytes, int is 4.
        ByteBuffer bytes = ByteBuffer.allocate(16);
        bytes.putInt(_sequenceNumber);
        bytes.putInt(_sequenceNumber);
        bytes.putInt(Integer.reverseBytes(_sequenceNumber));
        bytes.putInt(Integer.reverseBytes(_sequenceNumber));

        return UUID.nameUUIDFromBytes(bytes.array());
    }

    // TODO: Implement this
    //public static void AddHandlerFor<T>(Func<MemberInfo, int, T> func)
    //{
    //    throw new NotImplementedException();
    //}
    
}
