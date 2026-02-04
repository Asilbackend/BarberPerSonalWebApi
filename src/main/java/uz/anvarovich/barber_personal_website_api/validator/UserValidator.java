package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;

public final class UserValidator {
    private UserValidator() {

    }

    public static void validate(CreateUserReqDto dto) {
        if (dto == null || dto.phone() == null || dto.phone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefon raqami kiritilmagan");
        }

        String phone = dto.phone()
                .replaceAll("[^0-9]", "");  // faqat raqamlarni qoldiramiz

        if (phone.startsWith("998")) {
            phone = phone.substring(3);
        }

        if (phone.length() != 9) {
            throw new IllegalArgumentException("Telefon raqami 9 ta raqamdan iborat bo‘lishi kerak");
        }

        if (!phone.matches("^(20|50|77|75|33|88|9[0-9])\\d{7}$")) {
            throw new IllegalArgumentException("Noto‘g‘ri operator kodi. O‘zbekiston raqamlari 90,91,93,94,95,97,98,99,33,88 bilan boshlanadi");
        }
    }
}
