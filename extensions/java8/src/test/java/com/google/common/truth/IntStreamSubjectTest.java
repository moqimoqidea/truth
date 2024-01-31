/*
 * Copyright (c) 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.truth;

import static com.google.common.truth.FailureAssertions.assertFailureKeys;
import static com.google.common.truth.FailureAssertions.assertFailureValue;
import static com.google.common.truth.IntStreamSubject.intStreams;
import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for Java 8 {@link IntStream} Subjects.
 *
 * @author Kurt Alfred Kluever
 */
@RunWith(JUnit4.class)
public final class IntStreamSubjectTest {

  @Test
  public void testIsEqualTo() throws Exception {
    IntStream stream = IntStream.of(42);
    Truth8.assertThat(stream).isEqualTo(stream);
  }

  @Test
  public void testIsEqualToList() throws Exception {
    IntStream stream = IntStream.of(42);
    List<Integer> list = asList(42);
    AssertionError unused = expectFailure(whenTesting -> whenTesting.that(stream).isEqualTo(list));
  }

  @Test
  public void testNullStream_fails() throws Exception {
    IntStream nullStream = null;
    try {
      Truth8.assertThat(nullStream).isEmpty();
      fail();
    } catch (NullPointerException expected) {
    }
  }

  @Test
  public void testNullStreamIsNull() throws Exception {
    IntStream nullStream = null;
    Truth8.assertThat(nullStream).isNull();
  }

  @Test
  public void testIsSameInstanceAs() throws Exception {
    IntStream stream = IntStream.of(1);
    Truth8.assertThat(stream).isSameInstanceAs(stream);
  }

  @Test
  public void testIsEmpty() throws Exception {
    Truth8.assertThat(IntStream.of()).isEmpty();
  }

  @Test
  public void testIsEmpty_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).isEmpty());
  }

  @Test
  public void testIsNotEmpty() throws Exception {
    Truth8.assertThat(IntStream.of(42)).isNotEmpty();
  }

  @Test
  public void testIsNotEmpty_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of()).isNotEmpty());
  }

  @Test
  public void testHasSize() throws Exception {
    Truth8.assertThat(IntStream.of(42)).hasSize(1);
  }

  @Test
  public void testHasSize_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).hasSize(2));
  }

  @Test
  public void testContainsNoDuplicates() throws Exception {
    Truth8.assertThat(IntStream.of(42)).containsNoDuplicates();
  }

  @Test
  public void testContainsNoDuplicates_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42, 42)).containsNoDuplicates());
  }

  @Test
  public void testContains() throws Exception {
    Truth8.assertThat(IntStream.of(42)).contains(42);
  }

  @Test
  public void testContains_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).contains(100));
  }

  @Test
  public void testContainsAnyOf() throws Exception {
    Truth8.assertThat(IntStream.of(42)).containsAnyOf(42, 43);
  }

  @Test
  public void testContainsAnyOf_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).containsAnyOf(43, 44));
  }

  @Test
  public void testContainsAnyIn() throws Exception {
    Truth8.assertThat(IntStream.of(42)).containsAnyIn(asList(42, 43));
  }

  @Test
  public void testContainsAnyIn_fails() throws Exception {
    AssertionError unused =
        expectFailure(
            whenTesting -> whenTesting.that(IntStream.of(42)).containsAnyIn(asList(43, 44)));
  }

  @Test
  public void testDoesNotContain() throws Exception {
    Truth8.assertThat(IntStream.of(42)).doesNotContain(43);
  }

  @Test
  public void testDoesNotContain_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).doesNotContain(42));
  }

  @Test
  public void testContainsNoneOf() throws Exception {
    Truth8.assertThat(IntStream.of(42)).containsNoneOf(43, 44);
  }

  @Test
  public void testContainsNoneOf_fails() throws Exception {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42)).containsNoneOf(42, 43));
  }

  @Test
  public void testContainsNoneIn() throws Exception {
    Truth8.assertThat(IntStream.of(42)).containsNoneIn(asList(43, 44));
  }

  @Test
  public void testContainsNoneIn_fails() throws Exception {
    AssertionError unused =
        expectFailure(
            whenTesting -> whenTesting.that(IntStream.of(42)).containsNoneIn(asList(42, 43)));
  }

  @Test
  public void testContainsAtLeast() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsAtLeast(42, 43);
  }

  @Test
  public void testContainsAtLeast_fails() throws Exception {
    AssertionError unused =
        expectFailure(
            whenTesting -> whenTesting.that(IntStream.of(42, 43)).containsAtLeast(42, 43, 44));
  }

  @Test
  public void testContainsAtLeast_inOrder() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsAtLeast(42, 43).inOrder();
  }

  @Test
  public void testContainsAtLeast_inOrder_fails() throws Exception {
    AssertionError expected =
        expectFailure(
            whenTesting ->
                whenTesting.that(IntStream.of(42, 43)).containsAtLeast(43, 42).inOrder());
    assertFailureKeys(
        expected,
        "required elements were all found, but order was wrong",
        "expected order for required elements",
        "but was");
    assertFailureValue(expected, "expected order for required elements", "[43, 42]");
  }

  @Test
  public void testContainsAtLeastElementsIn() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsAtLeastElementsIn(asList(42, 43));
  }

  @Test
  public void testContainsAtLeastElementsIn_fails() throws Exception {
    AssertionError unused =
        expectFailure(
            whenTesting ->
                whenTesting
                    .that(IntStream.of(42, 43))
                    .containsAtLeastElementsIn(asList(42, 43, 44)));
  }

  @Test
  public void testContainsAtLeastElementsIn_inOrder() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsAtLeastElementsIn(asList(42, 43)).inOrder();
  }

  @Test
  public void testContainsAtLeastElementsIn_inOrder_fails() throws Exception {
    AssertionError expected =
        expectFailure(
            whenTesting ->
                whenTesting
                    .that(IntStream.of(42, 43))
                    .containsAtLeastElementsIn(asList(43, 42))
                    .inOrder());
    assertFailureKeys(
        expected,
        "required elements were all found, but order was wrong",
        "expected order for required elements",
        "but was");
    assertFailureValue(expected, "expected order for required elements", "[43, 42]");
  }

  @Test
  public void testContainsExactly() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsExactly(42, 43);
  }

  @Test
  public void testContainsExactly_fails() throws Exception {
    AssertionError expected =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(42, 43)).containsExactly(42));
    assertFailureKeys(expected, "unexpected (1)", "---", "expected", "but was");
    assertFailureValue(expected, "expected", "[42]");
  }

  @Test
  public void testContainsExactly_inOrder() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsExactly(42, 43).inOrder();
  }

  @Test
  public void testContainsExactly_inOrder_fails() throws Exception {
    AssertionError expected =
        expectFailure(
            whenTesting ->
                whenTesting.that(IntStream.of(42, 43)).containsExactly(43, 42).inOrder());
    assertFailureKeys(expected, "contents match, but order was wrong", "expected", "but was");
    assertFailureValue(expected, "expected", "[43, 42]");
  }

  @Test
  public void testContainsExactlyElementsIn() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsExactlyElementsIn(asList(42, 43));
    Truth8.assertThat(IntStream.of(42, 43)).containsExactlyElementsIn(asList(43, 42));
  }

  @Test
  public void testContainsExactlyElementsIn_fails() throws Exception {
    AssertionError expected =
        expectFailure(
            whenTesting ->
                whenTesting.that(IntStream.of(42, 43)).containsExactlyElementsIn(asList(42)));
    assertFailureKeys(expected, "unexpected (1)", "---", "expected", "but was");
    assertFailureValue(expected, "expected", "[42]");
  }

  @Test
  public void testContainsExactlyElementsIn_inOrder() throws Exception {
    Truth8.assertThat(IntStream.of(42, 43)).containsExactlyElementsIn(asList(42, 43)).inOrder();
  }

  @Test
  public void testContainsExactlyElementsIn_inOrder_fails() throws Exception {
    AssertionError expected =
        expectFailure(
            whenTesting ->
                whenTesting
                    .that(IntStream.of(42, 43))
                    .containsExactlyElementsIn(asList(43, 42))
                    .inOrder());
    assertFailureKeys(expected, "contents match, but order was wrong", "expected", "but was");
    assertFailureValue(expected, "expected", "[43, 42]");
  }

  @Test
  public void testContainsExactlyElementsIn_inOrder_intStream() throws Exception {
    Truth8.assertThat(IntStream.of(1, 2, 3, 4)).containsExactly(1, 2, 3, 4).inOrder();
  }

  @Test
  public void testIsInOrder() {
    Truth8.assertThat(IntStream.of()).isInOrder();
    Truth8.assertThat(IntStream.of(1)).isInOrder();
    Truth8.assertThat(IntStream.of(1, 1, 2, 3, 3, 3, 4)).isInOrder();
  }

  @Test
  public void testIsInOrder_fails() {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(1, 3, 2, 4)).isInOrder());
  }

  @Test
  public void testIsInStrictOrder() {
    Truth8.assertThat(IntStream.of()).isInStrictOrder();
    Truth8.assertThat(IntStream.of(1)).isInStrictOrder();
    Truth8.assertThat(IntStream.of(1, 2, 3, 4)).isInStrictOrder();
  }

  @Test
  public void testIsInStrictOrder_fails() {
    AssertionError unused =
        expectFailure(whenTesting -> whenTesting.that(IntStream.of(1, 2, 2, 4)).isInStrictOrder());
  }

  private static AssertionError expectFailure(
      ExpectFailure.SimpleSubjectBuilderCallback<IntStreamSubject, IntStream> assertionCallback) {
    return ExpectFailure.expectFailureAbout(intStreams(), assertionCallback);
  }
}
