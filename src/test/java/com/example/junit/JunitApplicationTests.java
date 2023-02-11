package com.example.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// assertions 추가
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JunitApplicationTests {

    @DisplayName("[정상] 생성자에 이름만 넣었을 때")
    @Test
    void  WhenConstructorGivenName() {
        // given
        Dog mimi  = new Dog("미미");
        // when & then
        assertThat(mimi)
                .isNotNull() // null 체크
                .isInstanceOf(Dog.class) // 클래스 타입 체크
                .hasFieldOrPropertyWithValue("name","미미")
                .hasFieldOrPropertyWithValue("age", 0)
                .hasFieldOrPropertyWithValue("gender",null); // 특정 필드 값체크
    }

    @DisplayName("[정상] 생성자에 이름만 넣었을 때")
    @Test
    void WhenConstructorGivenNameWithMatcher() {
        // given
        Dog mimi  = new Dog("미미");
        // when & then
    }
    @DisplayName("[정상] 생성자에 이름과 나이를 넣었을 때")
    @Test
    void WhenConstructorGivenNameAndAge() {
        // given
        Dog mimi  = new Dog("미미",12);
        // when & then
        assertThat(mimi)
                .isNotNull() // null 체크
                .isInstanceOf(Dog.class) // 클래스 타입 체크
                .hasFieldOrPropertyWithValue("name","미미")
                .hasFieldOrPropertyWithValue("age", 12)
                .hasFieldOrPropertyWithValue("gender",null); // 특정 필드 값체크
    }

    @DisplayName("[비정상] 생성자에 null 값이 들어 갔을 때")
    @Test
    void WhenConstructorGivenNull() {
        // given & when & then
        assertThatThrownBy(()-> {
            new Dog(null);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("이름이 이상해요.");
    }

    @DisplayName("[비정상] 생성자에 null 값이 들어 갔을 때 try catch")
    @Test
    void WhenConstructorGivenNullTryCatch() {
        // given & when & then
        try{
            Dog mimi = new Dog(null);
        }catch (Exception e){
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이름이 이상해요.");
        }
    }

    @DisplayName("[정상] 객체 비교하기")
    @Test
    void GivenDifferentTwoDogs() {
        // given
        Dog mimi = new Dog("미미");
        Dog bibi = new Dog("비비");

        // when & then
        assertThat(mimi).isNotEqualTo(bibi);
        assertThat(mimi).hasFieldOrPropertyWithValue("name","미미");
        assertThat(bibi).hasFieldOrPropertyWithValue("name","비비");
    }


    @DisplayName("[정상] list 필드 값 확인")
    @Test
    void GivenDogsArray() {
        // given
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog("미미",5));
        dogList.add(new Dog("비비",6));
        dogList.add(new Dog("윤지",7));
        dogList.add(new Dog("지민",8));

        // when & then
        assertThat(dogList)
                .extracting("name")
                .contains("미미","비비","윤지","지민");

        // 여러 개 필드 확인
        assertThat(dogList)
                .extracting("name","age")
                .contains(tuple("미미",5)
                        ,tuple("비비",6)
                        ,tuple("윤지",7)
                        ,tuple("지민",8)
                );

        //리스트 내 특정 조건 확인
        assertThat(dogList).allSatisfy(dog -> {
            assertThat(dog.getName()).isNotEqualTo("mike");
            assertThat(dog.getAge()).isLessThan(20);
        });

        //matcher 사용할 수 도 있다.
        assertThat(dogList).allMatch(dog -> dog.getName().length() > 0);
    }

    @DisplayName("[정상] map 필드 값 확인")
    @Test
    void GivenDogsMap() {
        Map<String,Dog> map = new HashMap<>();
        map.put("미미", new Dog("미미",4));
        map.put("비비", new Dog("비비",5));
        map.put("윤지", new Dog("윤지",6));


        // dog 객체는 이름만 같으면 같은 동물로 취급하기로 함! 그래서 아래는 같아야함
        // assertions will pass
        assertThat(map).contains(
                entry("미미", new Dog("미미"))
                , entry("비비",new Dog("비비")));

        assertThat(map).hasSize(3);
    }
}
