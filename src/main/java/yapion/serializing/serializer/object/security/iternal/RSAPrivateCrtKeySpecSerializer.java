// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020,2021 yoyosource

package yapion.serializing.serializer.object.security.iternal;

import yapion.hierarchy.types.YAPIONObject;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;

public class RSAPrivateCrtKeySpecSerializer implements KeySpecSerializer<RSAPrivateCrtKey, RSAPublicKey> {

    @Override
    public YAPIONObject serializePrivateKey(RSAPrivateCrtKey rsaPrivateCrtKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(rsaPrivateCrtKey.getAlgorithm());
        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = keyFactory.getKeySpec(rsaPrivateCrtKey, RSAPrivateCrtKeySpec.class);

        YAPIONObject yapionObject = new YAPIONObject();
        yapionObject.add("modulus", rsaPrivateCrtKeySpec.getModulus());
        yapionObject.add("publicExponent", rsaPrivateCrtKeySpec.getPublicExponent());
        yapionObject.add("privateExponent", rsaPrivateCrtKeySpec.getPrivateExponent());
        yapionObject.add("primeP", rsaPrivateCrtKeySpec.getPrimeP());
        yapionObject.add("primeQ", rsaPrivateCrtKeySpec.getPrimeQ());
        yapionObject.add("primeExponentP", rsaPrivateCrtKeySpec.getPrimeExponentP());
        yapionObject.add("primeExponentQ", rsaPrivateCrtKeySpec.getPrimeExponentQ());
        yapionObject.add("crtCoefficient", rsaPrivateCrtKeySpec.getCrtCoefficient());
        return yapionObject;
    }

    @Override
    public RSAPrivateCrtKey deserializePrivateKey(YAPIONObject yapionObject, String algorithm) throws Exception {
        BigInteger modulus = yapionObject.getValue("modulus", BigInteger.class).get();
        BigInteger publicExponent = yapionObject.getValue("publicExponent", BigInteger.class).get();
        BigInteger privateExponent = yapionObject.getValue("privateExponent", BigInteger.class).get();
        BigInteger primeP = yapionObject.getValue("primeP", BigInteger.class).get();
        BigInteger primeQ = yapionObject.getValue("primeQ", BigInteger.class).get();
        BigInteger primeExponentP = yapionObject.getValue("primeExponentP", BigInteger.class).get();
        BigInteger primeExponentQ = yapionObject.getValue("primeExponentQ", BigInteger.class).get();
        BigInteger crtCoefficient = yapionObject.getValue("crtCoefficient", BigInteger.class).get();

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return (RSAPrivateCrtKey) keyFactory.generatePrivate(new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent, primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient));
    }

    @Override
    public YAPIONObject serializePublicKey(RSAPublicKey rsaPublicKey) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public RSAPublicKey deserializePublicKey(YAPIONObject yapionObject, String algorithm) throws Exception {
        throw new UnsupportedOperationException();
    }

}