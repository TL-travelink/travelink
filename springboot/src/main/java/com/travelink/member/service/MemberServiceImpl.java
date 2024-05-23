package com.travelink.member.service;

import com.travelink.member.dto.BestMemberResponse;
import com.travelink.member.dto.FollowInfoDto;
import com.travelink.member.dto.FollowListDto;
import com.travelink.member.dto.ImageInfoDto;
import com.travelink.member.dto.MemberDto;
import com.travelink.member.dto.MemberInfoDto;
import com.travelink.member.dto.MemberInfoRequest;
import com.travelink.member.dto.MemberInfoResponse;
import com.travelink.member.dto.NewFollowDto;
import com.travelink.member.dto.ProfileDto;
import com.travelink.member.mapper.MemberMapper;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;
    @Value("${file.path}")
    private String uploadPath;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    @Value("${file.path.upload-files}")
    private String uploadFilePath;

    public MemberServiceImpl(final MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional
    public void createInfo(final String loginId, final MemberInfoRequest memberInfoRequest)
        throws IOException {
        //todo 이미 해당 아이디로 만들어져 있다면 예외발생

        MultipartFile image = memberInfoRequest.getImage();
        ImageInfoDto imageInfoDto = null;
        imageInfoDto = saveImage(image, imageInfoDto);

        memberMapper.createInfo(MemberInfoDto.of(loginId, memberInfoRequest, imageInfoDto));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoResponse getInfo(final String loginId) {
        return memberMapper.getInfo(loginId);
    }

    @Override
    @Transactional
    public void updateInfo(final String loginId, final MemberInfoRequest memberInfoRequest)
        throws IOException {
        MemberInfoResponse info = memberMapper.getInfo(loginId);
        ImageInfoDto imageInfoDto = null;

        if (info.getImage() != null) {
            System.out.println("탐");
            deleteImage(info.getImage().getSaveFile(), info);
        }
        imageInfoDto = saveImage(memberInfoRequest.getImage(), imageInfoDto);

        memberMapper.updateInfo(MemberInfoDto.of(loginId, memberInfoRequest, imageInfoDto));
    }

    @Override
    @Transactional
    public void deleteInfo(final String loginId) throws IOException {
        MemberInfoResponse info = memberMapper.getInfo(loginId);
        deleteImage(info.getImage().getSaveFile(), info);

        memberMapper.deleteInfo(loginId);
    }

    @Override
    @Transactional
    public void addFollowing(final String loginId, final String following) {
        FollowInfoDto followInfoDto = new FollowInfoDto(loginId, following);

        int count = memberMapper.findFollowing(followInfoDto);

        if (count > 0) {
            memberMapper.deleteFollowing(followInfoDto);
        }else {
            memberMapper.addFollowing(followInfoDto);
        }
    }

    @Override
    public FollowListDto getFollowing(String loginId) throws IOException {
        List<ProfileDto> following = memberMapper.getFollowing(loginId);
        for (ProfileDto profileDto : following) {
            profileDto.setProImg(encodingImage(profileDto.getProImg()));
        }

        return new FollowListDto(following);
    }

    @Override
    public FollowListDto getFollower(String loginId) throws IOException{
        List<ProfileDto> follower = memberMapper.getFollower(loginId);
        for(ProfileDto profileDto : follower) {
            profileDto.setProImg(encodingImage(profileDto.getProImg()));
        }

        return new FollowListDto(follower);
    }

    @Override
    public List<ProfileDto> search(String memberId) {
        return memberMapper.search(memberId);
    }

    @Override
    public String encodingImage(String img) throws IOException {
        if(img!=null) {
            String filePath = uploadPath + File.separator + "user" +File.separator + img;
            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //실제 파일 불러오기
            return  Base64.getEncoder().encodeToString(bytes); //인코딩
        }else{
            return null;
        }
    }

    @Override
    public List<BestMemberResponse> getBest3Member() throws IOException {
        List<BestMemberResponse> follower = memberMapper.getBest3Member();
        for(BestMemberResponse dto : follower) {
            dto.setProImg(encodingImage(dto.getProImg()));
        }

        return follower;
    }

    @Override
    public MemberDto getMember(String loginId){
        MemberDto result = memberMapper.getMember(loginId);
        try{
            result.setProImg(encodingImage(result.getProImg()));
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private ImageInfoDto saveImage(MultipartFile image, ImageInfoDto imageInfoDto)
        throws IOException {
        if (!image.isEmpty()) {
            String saveFolder = uploadPath + File.separator + "user";
            File folder = new File(saveFolder);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String originalFileName = image.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                String saveFileName = createSaveFileName(originalFileName);
                imageInfoDto = new ImageInfoDto(saveFolder, originalFileName, saveFileName);
                image.transferTo(new File(folder, saveFileName));
            }
        }
        return imageInfoDto;
    }

    private void deleteImage(String saveFile, MemberInfoResponse info) throws IOException {
        if (!saveFile.isEmpty()) {
            // 파일 경로
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = uploadPath + File.separator + today;
            String saveFileName = info.getImage().getSaveFile();
            Path filePath = Paths.get(saveFolder, saveFileName);

            // 파일 삭제
            Files.deleteIfExists(filePath);
        }
    }

    private String createSaveFileName(final String originalFileName) {
        return UUID.randomUUID()
            + originalFileName.substring(originalFileName.lastIndexOf('.'));
    }

    @Override
    public List<NewFollowDto> findMembers(String memberId) throws IOException {
        List<NewFollowDto> resutList = memberMapper.findMembers(memberId);
        for(NewFollowDto newFollowDto : resutList) {
            newFollowDto.setProImg(encodingImage(newFollowDto.getProImg()));
        }
        return resutList;
    }
}
