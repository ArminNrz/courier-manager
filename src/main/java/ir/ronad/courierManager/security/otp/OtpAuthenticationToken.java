package ir.ronad.courierManager.security.otp;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final String phoneNumber;

    private String otp;

    public OtpAuthenticationToken(String phoneNumber, String otp) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(String phoneNumber, String otp,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getPrincipal() {
        return phoneNumber;
    }

    @Override
    public Object getCredentials() {
        return otp;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        otp = null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to authenticated; " +
                    "use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
}
