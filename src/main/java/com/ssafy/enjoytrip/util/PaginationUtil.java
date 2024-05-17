package com.ssafy.enjoytrip.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {
    /**
     * 페이지네이션 버튼에 들어갈 숫자를 만들어주는 함수입니다.
     * @param page (int)
     * @param totalPage (int)
     *
     * @return 페이지번호 리스트 (List<Integer>)
     * */
    public static List<String> getPages(int page, int totalPage) {
        List<String> pages = new ArrayList<>();
        // 전체 페이지가 9개 이하인 경우는 1,2,3,4,5,6,7,8,9 를 전달
        if(totalPage <= 9) {
            for(int i=1; i<=totalPage; i++) {
                pages.add(Integer.toString(i));
            }
            return pages;
        }

        // 전체 페이지가 10개 이상인 경우
        pages.add("1");
        if(page < 6) {
            // 현재 페이지가 6이하인 경우, (1 2 3 4 5 6 7 ... 마지막페이지) 를 전달
            // page 변수의 값에 5가 들어있다는 것은 6페이지라는 것. 즉, page + 1이 실제 페이지임.
            pages.add("2");
            pages.add("3");
            pages.add("4");
            pages.add("5");
            pages.add("6");
            pages.add("7");
            pages.add("...");
            pages.add(Integer.toString(totalPage));
        } else if (page >= totalPage - 6) {
            // 현재 페이지가 뒤에서 6번째 이하인 경우, 마지막을 100이라하면 (1 ... 94 95 96 97 98 99 100)
            pages.add("...");
            pages.add(Integer.toString(totalPage-6));
            pages.add(Integer.toString(totalPage-5));
            pages.add(Integer.toString(totalPage-4));
            pages.add(Integer.toString(totalPage-3));
            pages.add(Integer.toString(totalPage-2));
            pages.add(Integer.toString(totalPage-1));
            pages.add(Integer.toString(totalPage));
        } else {
            // 현재 페이지가 7 ~ 마지막 - 6 인 경우, 현재 페이지를 50이라하면 (1 ... 48 49 50 51 52 ... 마지막)
            pages.add("...");
            pages.add(Integer.toString(page-2));
            pages.add(Integer.toString(page-1));
            pages.add(Integer.toString(page));
            pages.add(Integer.toString(page+1));
            pages.add(Integer.toString(page+2));
            
            pages.add("...");
            pages.add(Integer.toString(totalPage));
        }
        return pages;
    }
}
