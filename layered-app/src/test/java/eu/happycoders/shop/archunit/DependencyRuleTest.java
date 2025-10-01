package eu.happycoders.shop.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class DependencyRuleTest {

  private static final String ROOT_PACKAGE = "eu.happycoders.shop";
  private static final String MODEL_PACKAGE = "model";
  private static final String PERSISTENCE_PACKAGE = "persistence";
  private static final String REST_PACKAGE = "rest";
  private static final String SERVICE_PACKAGE = "service";

  @Test
  void checkDependencyRule() {
    String importPackages = ROOT_PACKAGE + "..";
    JavaClasses classesToCheck = new ClassFileImporter().importPackages(importPackages);

    checkNoDependencyFromTo(MODEL_PACKAGE, PERSISTENCE_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(MODEL_PACKAGE, SERVICE_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(MODEL_PACKAGE, REST_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(PERSISTENCE_PACKAGE, SERVICE_PACKAGE, classesToCheck);
    checkNoDependencyFromTo(PERSISTENCE_PACKAGE, REST_PACKAGE, classesToCheck);

    checkNoDependencyFromTo(SERVICE_PACKAGE, REST_PACKAGE, classesToCheck);
  }

  private void checkNoDependencyFromTo(
      String fromPackage, String toPackage, JavaClasses classesToCheck) {
    noClasses()
        .that()
        .resideInAPackage(fullyQualified(fromPackage))
        .should()
        .dependOnClassesThat()
        .resideInAPackage(fullyQualified(toPackage))
        .check(classesToCheck);
  }

  private String fullyQualified(String packageName) {
    return ROOT_PACKAGE + '.' + packageName + "..";
  }
}
