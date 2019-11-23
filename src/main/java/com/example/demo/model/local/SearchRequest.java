package com.example.demo.model.local;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SearchRequest {
  public static final int MIN_TERM_LENGTH = 3;
  private static final String TERM_SPLITTER = "[\\s,;]+";
  private static final Pattern POTENTIAL_ID_PATTERN = Pattern.compile("^№?\\d*\\/?\\d*");
  private static final Predicate<String> POTENTIAL_ID = s -> POTENTIAL_ID_PATTERN.matcher(s).matches();

  private static final Comparator<String> TERM_COMPARATOR = (a, b) -> {
    int result;

    boolean aId = POTENTIAL_ID.test(a), bId = POTENTIAL_ID.test(b);
    result = aId == bId ? 0 : aId ? -1 : 1;
    if (result != 0) return result;

    result = b.length() - a.length();
    if (result != 0) return result;

    return a.compareTo(b);
  };

  public final String originalQuery;
  public final String finalQuery;
  private final String[] terms;
  private final String[] validTerms;

  public SearchRequest(String query) {
    this.originalQuery = query;
    this.terms = query.toLowerCase().split(TERM_SPLITTER);
    Set<String> validTerms = new LinkedHashSet<>(terms.length);
    for (int i = 0; i < terms.length; ++i)
      if (terms[i].length() >= MIN_TERM_LENGTH) validTerms.add(terms[i]);
    this.validTerms = validTerms.toArray(new String[validTerms.size()]);
    Arrays.sort(this.validTerms, TERM_COMPARATOR);
    this.finalQuery = String.join(" ", this.validTerms);
  }

  public boolean isEmpty() {
    return finalQuery.isEmpty();
  }

  public String[] getTerms() {
    return terms;
  }

  public String[] getValidTerms() {
    return validTerms;
  }
}
