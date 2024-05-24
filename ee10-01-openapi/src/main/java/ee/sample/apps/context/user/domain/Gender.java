package ee.sample.apps.context.user.domain;

import org.vermeerlab.base.domain.utils.enumclass.EnumReverseLookup;

/** 性別. */
@lombok.AllArgsConstructor
@lombok.Getter
public enum Gender {
  MALE("0"),
  FEMALE("1"),
  OTHER("2");

  private final String cd;

  public static final EnumReverseLookup<Gender, String> byCd =
      new EnumReverseLookup<>(Gender.class, Gender::getCd);
}
