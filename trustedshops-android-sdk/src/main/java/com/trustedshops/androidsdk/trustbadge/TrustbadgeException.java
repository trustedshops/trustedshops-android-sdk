package com.trustedshops.androidsdk.trustbadge;

public class TrustbadgeException extends Exception
{
    public TrustbadgeException() {
        super();
    }

    public TrustbadgeException(String message)
    {
        super(message);
    }

    public TrustbadgeException(Throwable cause)

    {
        super(cause);
    }


    public TrustbadgeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
