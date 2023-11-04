package com.example.dividendproject.service;

import com.example.dividendproject.exception.input.AlreadyExistUserException;
import com.example.dividendproject.exception.input.NoUsernameException;
import com.example.dividendproject.exception.input.PasswordException;
import com.example.dividendproject.model.Auth;
import com.example.dividendproject.model.MemberEntity;
import com.example.dividendproject.persist.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if(exists) {
            throw new AlreadyExistUserException();
        }
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity());

        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        var user = this.memberRepository.findByUsername(member.getUsername())
            .orElseThrow(NoUsernameException::new);

        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new PasswordException();
        }

        return user;
    }
}
