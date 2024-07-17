package com.ismhac.jspace.dto.common.dictionary;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    public static Map<String, Map<String, String>> experienceDictionary = new HashMap<>() {
        {
            put("NO_EXPERIENCE", new HashMap<>() {{
                put("en", "No Experience");
                put("vi", "Không yêu cầu kinh nghiệm");
            }});
            put("LESS_THAN_ONE_YEAR", new HashMap<>() {{
                put("en", "Less than one year");
                put("vi", "Dưới 1 năm kinh nghiệm");
            }});
            put("ONE_TO_THREE_YEARS", new HashMap<>() {{
                put("en", "One to Three year");
                put("vi", "Từ 1 - 3 năm kinh nghiệm");
            }});
            put("THREE_TO_FIVE_YEARS", new HashMap<>() {{
                put("en", "Three to five year");
                put("vi", "Từ 3 - 5 năm kinh nghiệm");
            }});
            put("MORE_THAN_FIVE_YEARS", new HashMap<>() {{
                put("en", "More than five year");
                put("vi", "Hơn 5 năm kinh nghiệm");
            }});
        }
    };

    public static Map<String, Map<String, String>> jobTypeDictionary = new HashMap<>() {{
        put("FULL_TIME", new HashMap<>() {{
            put("en", "Full time");
            put("vi", "Toàn thời gian");
        }});
        put("PART_TIME", new HashMap<>() {{
            put("en", "Part time");
            put("vi", "Bán thời gian");
        }});
        put("TEMPORARY", new HashMap<>() {{
            put("en", "Temporary");
            put("vi", "Tạm thời");
        }});
        put("CONTRACT", new HashMap<>() {{
            put("en", "Contract");
            put("vi", "Hợp đồng");
        }});
        put("INTERNSHIP", new HashMap<>() {{
            put("en", "Internship");
            put("vi", "Thực tập sinh");
        }});
        put("NEW_GRAD", new HashMap<>() {{
            put("en", "New grad");
            put("vi", "Sinh viên mới ra trường");
        }});
        put("REMOTE", new HashMap<>() {{
            put("en", "Remote");
            put("vi", "Làm việc từ xa");
        }});
    }};

    public static Map<String, Map<String, String>> genderDictionary = new HashMap<>(){{
        put("MALE", new HashMap<>(){{
            put("en", "Male");
            put("vi", "Nam");
        }});
        put("FEMALE", new HashMap<>(){{
            put("en", "Female");
            put("vi", "Nữ");
        }});
        put("OTHER", new HashMap<>(){{
            put("en", "Unisex");
            put("vi", "Cả nam và nữ");
        }});
    }};

    public static Map<String, Map<String, String>> applyStatusDictionary = new HashMap<>(){{
        put("PROGRESS", new HashMap<>(){{
            put("en", "Progress");
            put("vi", "Đang xét duyệt");
        }});
        put("APPROVE", new HashMap<>(){{
            put("en", "Approve");
            put("vi", "Phê duyệt");
        }});
        put("REJECT", new HashMap<>(){{
            put("en", "Reject");
            put("vi", "Từ chối");
        }});
    }} ;

    public static Map<String, Map<String, String>> rankDictionary = new HashMap<>(){{
       put("INTERN", new HashMap<>(){{
           put("en", "Intern");
           put("vi", "Intern");
       }}) ;
        put("FRESHER", new HashMap<>(){{
            put("en", "Fresher");
            put("vi", "Fresher");
        }}) ;
        put("JUNIOR", new HashMap<>(){{
            put("en", "Junior");
            put("vi", "Junior");
        }}) ;
        put("MIDDlE", new HashMap<>(){{
            put("en", "Middle");
            put("vi", "Middle");
        }}) ;
        put("SENIOR", new HashMap<>(){{
            put("en", "Senior");
            put("vi", "Senior");
        }}) ;
        put("TEAM_LEADER", new HashMap<>(){{
            put("en", "Team leader");
            put("vi", "Team leader");
        }}) ;
        put("DEPARTMENT_HEAD", new HashMap<>(){{
            put("en", "Department head");
            put("vi", "Department head");
        }}) ;
        put("ALL_LEVELS", new HashMap<>(){{
            put("en",  "All levels");
            put("vi", "Tất cả cấp bậc");
        }});
    }};
}
