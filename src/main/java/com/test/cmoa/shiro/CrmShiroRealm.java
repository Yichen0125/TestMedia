package com.test.cmoa.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.cmoa.entity.Authority;
import com.test.cmoa.entity.User;
import com.test.cmoa.service.UserService;

@Service
public class CrmShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		User user = (User) arg0.getPrimaryPrincipal();
		List<Authority> authorities = user.getRole().getAuthorities();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Authority authority : authorities) {
			System.out.println(authority.getName());
			info.addRole(authority.getName());
		}

		return info;
	}

	@Autowired
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		User user = userService.getByUserName(username);

		if (user == null) {
			throw new UnknownAccountException("该用户不存在.");
		}

		if (user.getEnabled() != 1) {
			throw new LockedAccountException("该用户被锁定.");
		}

		Object principal = user;
		Object hashedCredentials = user.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt,
				realmName);

		return info;
	}

	// @PostConstruct 相当于配置文件中的 init-method
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}

	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		String credentials = "123123";
		ByteSource salt = ByteSource.Util.bytes("ceadfd47cdaa814c");
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);

		System.out.println(result);
	}
}
