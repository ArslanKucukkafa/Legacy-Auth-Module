package com.arslankucukkafa.labormarketauth.util.citizenship;

// Maybe we will a global e-commerce platform and we need to verify the citizenship of the users. Wish we had a class for that.
public interface VerifyCtizenship {
    // This method will verify the citizenship of the user.
    boolean verifyCtizenship(String tcKimlikNo, String firstName, String lastName, int birthYear);

}
