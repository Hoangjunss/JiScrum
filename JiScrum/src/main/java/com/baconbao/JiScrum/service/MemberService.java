package com.baconbao.JiScrum.service;


import com.baconbao.JiScrum.dto.member.MemberCreateDTO;
import com.baconbao.JiScrum.dto.member.MemberDTO;
import com.baconbao.JiScrum.dto.member.MemberUpdateDTO;
import com.baconbao.JiScrum.model.Member;
import org.apache.coyote.BadRequestException;


public interface MemberService {

    MemberDTO createMember(MemberCreateDTO dto) throws BadRequestException;
    MemberDTO updateMember(Integer id, MemberUpdateDTO dto);
    MemberDTO getMemberById(Integer id);
    Member getMemberEntityById(Integer id);
    void deleteMember(Integer id);
}
