/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.merovingian.jbuilder.generators;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author jasonr
 */
public class RandomGeneratorImpl implements RandomGenerator {
    
        private final Random rnd;

        private static Date minDate = new Date(0);
        private static Date maxDate = new Date(Long.MAX_VALUE);

        private static final String[] latinWords = { "lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipisicing", "elit", "sed", "do", "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua" };

        public RandomGeneratorImpl() { 
            this(UUID.randomUUID().hashCode());
        }

        public RandomGeneratorImpl(int seed) {
            this(new Random(seed));
        }

        public RandomGeneratorImpl(Random random)
        {
            rnd = random;
        }

        public short Next(short min, short max)
        {
            return (short)Next((int)min, max);
        }

        public int Next(int min, int max)
        {
            return rnd.nextInt(max - min + 1) + min;
        }

        public long Next(long min, long max)
        {
            double rn = (max * 1.0 - min * 1.0) * rnd.nextDouble() + min * 1.0;
            return Double.doubleToLongBits(rn);
        }

        public float Next(float min, float max)
        {
            return (float)Next((int)min, (int)max);
        }

        public double Next(double min, double max)
        {
            return (double)Next((int)min, (int)max);
        }

//        public virtual decimal Next(decimal min, decimal max)
//        {
//            if (min < int.MinValue)
//                min = (decimal) int.MinValue;
//
//            if (max > int.MaxValue)
//                max = (decimal) int.MaxValue;
//
//            int iMin = (int)min;
//            int iMax = (int)max;
//
//            int integer = rnd.Next(iMin, iMax);
//            int fraction = rnd.Next(0, 4000);
//
//            return (decimal)Convert.ToDecimal(string.Format("{0}.{1}", integer, fraction));
//        }

        public char Next(char min, char max)
        {
            return (char)Next((int)min, (int)max);
        }

        public byte Next(byte min, byte max)
        {
            return (byte)Next((int)min, (int)max);
        }

//        public sbyte Next(sbyte min, sbyte max)
//        {
//            return (sbyte)Next((int)min, (int)max);
//        }

        public Date Next(Date min, Date max)
        {
            long minTicks = min.getTime();
            long maxTicks = max.getTime();
            double rn = (Double.longBitsToDouble(maxTicks)
               - Double.longBitsToDouble(minTicks)) * rnd.nextDouble()
               + Double.longBitsToDouble(minTicks);
            return new Date(Double.doubleToLongBits(rn));

        }

        public boolean Next()
        {
            return rnd.nextInt(2) == 1;
        }

        public UUID Guid()
        {
            return UUID.randomUUID();
        }

        public boolean Boolean()
        {
            return Next(0, 2) != 0;
        }

        public int Int()
        {
            return Next(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        public short Short()
        {
            return Next(Short.MIN_VALUE, Short.MAX_VALUE);
        }

        public long Long()
        {
            return Next(Long.MIN_VALUE, Long.MAX_VALUE);
        }

//        public uint UInt()
//        {
//            return Next(uint.MinValue, uint.MaxValue);
//        }
//
//        public ulong ULong()
//        {
//            return Next(ulong.MinValue, ulong.MaxValue);
//        }
//
//        public ushort UShort()
//        {
//            return Next(ushort.MinValue, ushort.MaxValue);
//        }
//
//        public decimal Decimal()
//        {
//            return Next(decimal.MinValue, decimal.MaxValue);
//        }

        public float Float()
        {
            return Next(Float.MIN_VALUE, Float.MAX_VALUE);
        }

        public double Double()
        {
            return Next(Double.MIN_VALUE, Double.MAX_VALUE);
        }

        public byte Byte()
        {
            return Next(Byte.MIN_VALUE, Byte.MAX_VALUE);
        }

//        public sbyte SByte()
//        {
//            return Next(sbyte.MinValue, sbyte.MaxValue);
//        }

        public Date DateTime()
        {
            return Next(minDate, maxDate);
        }

        public String Phrase(int length)
        {
            int count = latinWords.length;
            String result = "";
            boolean done = false;
            while (!done)
            {
                String word = latinWords[Next(0, count - 1)];
                if (result.length() + word.length() + 1 > length)
                {
                    done = true;
                }
                else
                {
                    result += word + " ";
                }
            }
            return result.trim();
        }

        public char Char()
        {
            return Next(Character.MIN_VALUE, Character.MAX_VALUE);
        }

//        public ushort Next(ushort min, ushort max)
//        {
//            return (ushort)Next((int)min, (int)max);
//        }
//
//        public uint Next(uint min, uint max)
//        {
//            byte[] buffer = new byte[sizeof(uint)];
//            rnd.NextBytes(buffer);
//            return BitConverter.ToUInt32(buffer, 0);
//        }
//
//        public ulong Next(ulong min, ulong max)
//        {
//            byte[] buffer = new byte[sizeof(ulong)];
//            rnd.NextBytes(buffer);
//            return BitConverter.ToUInt64(buffer, 0);
//        }

        //TODO: ???
//        public T Enumeration<T>() where T : struct
//        {
//            var values = EnumHelper.GetValues(typeof(T));
//            var index = Next(0, values.Length);
//            return (T)values.GetValue(index);
//        }

        public Enum Enumeration(Type type)
        {
            if (!(type instanceof Enum))
            {
                throw new IllegalArgumentException(String.format("{0} is not an enum type.", type.toString()), new Throwable());
            }
            
            Object[] values = ((Enum)type).getDeclaringClass().getEnumConstants();
            int index = Next(0, values.length);
            return (Enum)values[index];
        }   

        // TODO: Implement NextString()
        //public virtual string NextString(int minLength, int maxLength)
        //{

        //}
    
}
