package com.ismhac.jspace.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Location {
    HA_NOI(24, "Hà Nội"),
    HO_CHI_MINH(28, "Hồ Chí Minh"),
    AN_GIANG(296, "An Giang"),
    BA_RIA_VUNG_TAU(254, "Bà Rịa - Vũng Tàu"),
    BAC_LIEU(291, "Bạc Liêu"),
    BAC_KAN(209, "Bắc Kạn"),
    BAC_GIANG(204, "Bắc Giang"),
    BAC_NINH(222, "Bắc Ninh"),
    BEN_TRE(275, "Bến Tre"),
    BINH_DUONG(274, "Bình Dương"),
    BINH_PHUOC(271, "Bình Phước"),
    BINH_THUAN(252, "Bình Thuận"),
    CA_MAU(290, "Cà Mau"),
    CAN_THO(292, "Cần Thơ"),
    DA_NANG(236, "Đà Nẵng"),
    DAK_LAK(262, "Đắk Lắk"),
    DAK_NONG(261, "Đắk Nông"),
    DIEN_BIEN(215, "Điện Biên"),
    DONG_NAI(251, "Đồng Nai"),
    DONG_THAP(277, "Đồng Tháp"),
    GIA_LAI(269, "Gia Lai"),
    HA_GIANG(219, "Hà Giang"),
    HA_NAM(226, "Hà Nam"),
    HA_TINH(239, "Hà Tĩnh"),
    HAI_DUONG(220, "Hải Dương"),
    HAI_PHONG(225, "Hải Phòng"),
    HAU_GIANG(293, "Hậu Giang"),
    HOA_BINH(218, "Hòa Bình"),
    HUNG_YEN(221, "Hưng Yên"),
    KHANH_HOA(258, "Khánh Hòa"),
    KIEN_GIANG(297, "Kiên Giang"),
    KON_TUM(260, "Kon Tum"),
    LAI_CHAU(213, "Lai Châu"),
    LANG_SON(205, "Lạng Sơn"),
    LAO_CAI(214, "Lào Cai"),
    LAM_DONG(263, "Lâm Đồng"),
    LONG_AN(272, "Long An"),
    NAM_DINH(228, "Nam Định"),
    NGHE_AN(238, "Nghệ An"),
    NINH_BINH(229, "Ninh Bình"),
    NINH_THUAN(259, "Ninh Thuận"),
    PHU_THO(210, "Phú Thọ"),
    PHU_YEN(257, "Phú Yên"),
    QUANG_BINH(232, "Quảng Bình"),
    QUANG_NAM(235, "Quảng Nam"),
    QUANG_NGAI(255, "Quảng Ngãi"),
    QUANG_NINH(203, "Quảng Ninh"),
    QUANG_TRI(233, "Quảng Trị"),
    SOC_TRANG(299, "Sóc Trăng"),
    SON_LA(212, "Sơn La"),
    TAY_NINH(276, "Tây Ninh"),
    THAI_BINH(227, "Thái Bình"),
    THAI_NGUYEN(208, "Thái Nguyên"),
    THANH_HOA(237, "Thanh Hóa"),
    THUA_THIEN_HUE(234, "Thừa Thiên Huế"),
    TIEN_GIANG(273, "Tiền Giang"),
    TRA_VINH(294, "Trà Vinh"),
    TUYEN_QUANG(207, "Tuyên Quang"),
    VINH_LONG(270, "Vĩnh Long"),
    VINH_PHUC(211, "Vĩnh Phúc"),
    YEN_BAI(216, "Yên Bái");
    Integer areaCode;
    String province;
}
