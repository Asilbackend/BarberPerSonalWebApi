package uz.anvarovich.barber_personal_website_api.dto.req_dto;

public record CreateUserReqDto(
        String fullName,
        String phone,
        String username,
        String password,
        String rePassword

) {
}
