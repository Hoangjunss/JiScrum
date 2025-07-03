package com.baconbao.JiScrum.service;


import com.baconbao.JiScrum.dto.member.MemberCreateDTO;
import com.baconbao.JiScrum.dto.member.MemberDTO;
import com.baconbao.JiScrum.dto.member.MemberFilter;
import com.baconbao.JiScrum.dto.member.MemberUpdateDTO;
import com.baconbao.JiScrum.model.Member;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;


public interface MemberService {

    MemberDTO createMember(MemberCreateDTO dto) throws BadRequestException;
    MemberDTO updateMember(Integer id, MemberUpdateDTO dto);
    MemberDTO getMemberById(Integer id);
    Member getMemberEntityById(Integer id);
    void deleteMember(Integer id);
    Member getMemberByProjectAndAccount(Integer projectId, Integer account);
    Page<MemberDTO> filter(MemberFilter memberFilter, int page, int size);
}
