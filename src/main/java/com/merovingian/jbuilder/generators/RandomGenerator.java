/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.generators;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author jasonr
 */
public interface RandomGenerator {
    
//    ushort Next(ushort min, ushort max);
//    uint Next(uint min, uint max);
//    ulong Next(ulong min, ulong max);

    short Next(short min, short max);

    int Next(int min, int max);

    long Next(long min, long max);

    float Next(float min, float max);

    double Next(double min, double max);

    //decimal Next(decimal min, decimal max);

    char Next(char min, char max);

    byte Next(byte min, byte max);

    //sbyte Next(sbyte min, sbyte max);

    Date Next(Date min, Date max);

    boolean Next();

    // TODO: Add NextString() to this interface
    //string NextString(int minLength, int maxLength);
    boolean Boolean();

    int Int();

    short Short();

    long Long();

//    uint UInt();
//
//    ulong ULong();
//
//    ushort UShort();
//
//    decimal Decimal();

    float Float();

    double Double();

    byte Byte();

    //sbyte SByte();

    Date DateTime();

    String Phrase(int length);

    char Char();

    UUID Guid();
    
    //TODO: ???
    //T Enumeration<T>() where T : struct ;

    Enum Enumeration(Type type);
}
