package org.openmrs.module.eptsreports;

import java.util.*;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.eptsreports.metadata.CommonMetadata;
import org.openmrs.module.eptsreports.metadata.HivMetadata;
import org.openmrs.module.eptsreports.metadata.TbMetadata;
import org.openmrs.module.eptsreports.reporting.calculation.txml.StartedArtOnLastClinicalContactCalculation;
import org.openmrs.module.eptsreports.reporting.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.eptsreports.reporting.intergrated.utils.DefinitionsFGHLiveTest;
import org.openmrs.module.eptsreports.reporting.library.cohorts.*;
import org.openmrs.module.eptsreports.reporting.library.datasets.IntensiveMonitoringDataSet;
import org.openmrs.module.eptsreports.reporting.library.datasets.TPTInitiationNewDataSet;
import org.openmrs.module.eptsreports.reporting.utils.EptsReportConstants;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.test.SkipBaseSetup;
import org.springframework.beans.factory.annotation.Autowired;

@SkipBaseSetup
public class LiveTests extends DefinitionsFGHLiveTest {
  @Autowired private TxMlCohortQueries txMlCohortQueries;
  @Autowired private GenericCohortQueries genericCohortQueries;

  @Autowired private ResumoMensalCohortQueries resumoMensalCohortQueries;
  @Autowired private ResumoTrimestralCohortQueries resumoTrimestralCohortQueries;
  @Autowired private Eri4MonthsCohortQueries eri4MonthsCohortQueries;
  @Autowired private EriCohortQueries eriCohortQueries;
  @Autowired private EriDSDCohortQueries eriDSDCohortQueries;
  @Autowired private TxRttCohortQueries txRttCohortQueries;
  @Autowired private TxCurrCohortQueries txCurrCohortQueries;
  @Autowired private CommonCohortQueries commonCohortQueries;
  @Autowired private QualityImprovement2020CohortQueries qualityImprovement2020CohortQueries;
  @Autowired private IntensiveMonitoringCohortQueries intensiveMonitoringCohortQueries;
  @Autowired private IntensiveMonitoringDataSet intensiveMonitoringDataSet;
  @Autowired private FaltososLevantamentoARVCohortQueries faltososLevantamentoARVCohortQueries;
  @Autowired private HivMetadata hivMetadata;
  @Autowired private CommonMetadata commonMetadata;
  @Autowired private TXTBCohortQueries txtbCohortQueries;
  @Autowired private TbMetadata tbMetadata;
  @Autowired private TPTEligiblePatientListCohortQueries tptEligiblePatientListCohortQueries;
  @Autowired private TPTInitiationCohortQueries tptInitiationCohortQueries;
  @Autowired private TPTInitiationDataDefinitionQueries tptInitiationDataDefinitionQueries;
  @Autowired private TPTCompletionCohortQueries tptCompletionCohortQueries;
  @Autowired private TbPrevCohortQueries tbPrevCohortQueries;
  @Autowired private TPTInitiationNewDataSet tptInitiationNewDataSet;
  @Autowired private HivCohortQueries hivCohortQueries;
  @Autowired private TransferredInCohortQueries transferredInCohortQueries;

  @Autowired private APSSResumoTrimestralCohortQueries apssResumoTrimestralCohortQueries;

  @Autowired
  private ViralLoadIntensiveMonitoringCohortQueries viralLoadIntensiveMonitoringCohortQueries;

  @Autowired
  private ListOfPatientsWithPositiveTbScreeningCohortQueries
      listOfPatientsWithPositiveTbScreeningCohortQueries;

  @Autowired private TxTbMonthlyCascadeCohortQueries txTbMonthlyCascadeCohortQueries;
  @Autowired private TxPvlsCohortQueries txPvlsCohortQueries;

  // Replace with your DB Username
  private final String dbUsername = "openmrs";

  // Replace with your DB Password
  private final String dbPassword = "openmrs";

  // Replace with your MySQL server IP/Domain
  private final String dbConnectionUrl = "jdbc:mysql://localhost:3320/openmrs?useSSL=false";
  //  ariel-db-v2   -------> thebugatti.e-saude.net:3320
  //  ccs-db-v2     -------> thebugatti.e-saude.net:3321
  //  ccs2-db-v2     -------> thebugatti.e-saude.net:3331
  //  egpaf-db-v2   -------> thebugatti.e-saude.net:3322
  //  fgh-db-v2     -------> thebugatti.e-saude.net:3324
  //  fgh2-db-v2    -------> thebugatti.e-saude.net:3326
  //  fgh3-db-v2    -------> thebugatti.e-saude.net:3327
  //  icap-db-v2    -------> thebugatti.e-saude.net:3328
  //  jhpiego-db-v2  -------> thebugatti.e-saude.net:3329
  //  echo-db        -------> thebugatti.e-saude.net:3332

  //  ariel: http://thebugatti.e-saude.net/ariel-v2 DB port: 3320
  //  ccs: http://thebugatti.e-saude.net/ccs-v3 DB port: 3321
  //  echo: http://thebugatti.e-saude.net/echo-v3  DB port: 3332
  //  egpaf: issue with database (work aroung on going)
  //  fgh: http://thebugatti.e-saude.net/fgh-v3-c4 DB port: 3337
  //  fgh2: http://thebugatti.e-saude.net/fgh2-v3-c4 DB port: 3341
  //  fgh3: http://thebugatti.e-saude.net/fgh3-v3-c4 DB port: 3342
  //  icap: http://thebugatti.e-saude.net/icap-v3 DB port: 3333
  //  jhpiego: http://thebugatti.e-saude.net/jhpiego-v3 DB port: 3329

  // IF YOU GET THE FOLLOWING EXCEPTION
  // java.lang.NullPointerException at
  // org.openmrs.api.context.UserContext.setUserLocation(UserContext.java:440)

  // RUN THE FOLLOWING UPDATE ON YOUR OPENMRS DB:

  // update user_property set property_value = (select location_id from (select location_id,
  // count(location_id) as total  from encounter group by location_id
  // desc limit 1) as default_location) where property = 'defaultLocation' and user_id = (select
  // user_id from users where username = 'admin');





//TEST FOR COHORTDEFINITIONS
  @Test
  public void TestE() throws EvaluationException {

    EvaluatedCohort baseCohort = evaluateCohortDefinition(genericCohortQueries.getBaseCohort());

    CohortDefinition cd =
        txPvlsCohortQueries.getPatientsWhoArePregnantOrBreastfeedingBasedOnParameter(
            EptsReportConstants.PregnantOrBreastfeedingWomen.BREASTFEEDINGWOMEN, null);

    System.out.println(cd.getName());
    EvaluationContext context = new EvaluationContext();
    // context.setBaseCohort(baseCohort);
    //    context.setBaseCohort(new Cohort("basecohort", "", new Integer[] {11855}));

    // context.addParameterValue("startDate", getStartDate());
    // context.addParameterValue("endDate", getEndDate());
    // context.addParameterValue("revisionEndDate", getRevisionEndDate());
    context.addParameterValue("location", getLocation());
    context.addParameterValue("onOrAfter", getStartDate());
    context.addParameterValue("onOrBefore", getEndDate());
    //    context.addParameterValue("cohortStartDate", getStartDate());
    //    context.addParameterValue("cohortEndDate", getEndDate());
    //    context.addParameterValue("reportingEndDate", getEndDate());
    //    context.addParameterValue("locations", getLocation());

    EvaluatedCohort eval = Context.getService(CohortDefinitionService.class).evaluate(cd, context);

    Set<Integer> patients = eval.getMemberIds();
    for (Integer i : patients) {
      System.out.println(i);
    }

    System.out.println("size:=> " + patients.size());
    /*    System.out.println(eval);
    System.out.println(eval.getCommaSeparatedPatientIds());*/
  }


  // TEST FOR CALCULATIONS
  @Test
  public void m() throws EvaluationException {
    CalculationCohortDefinition cd =
        new CalculationCohortDefinition(
            Context.getRegisteredComponents(StartedArtOnLastClinicalContactCalculation.class)
                .get(0));
    cd.addCalculationParameter("lessThan90Days", false);
    cd.addParameter(new Parameter("onOrAfter", "start", Date.class));
    cd.addParameter(new Parameter("onOrBefore", "end", Date.class));
    cd.addParameter(new Parameter("location", "location", Location.class));
    EvaluatedCohort evaluated = evaluateCalculationCohortDefinition(cd);
    Set<Integer> patients = evaluated.getMemberIds();
    for (Integer i : patients) {
      System.out.println(i);
    }
    System.out.println("size:=> " + patients.size());

    // return cd;
  }



  //TEST FOR DATASETDEFINITION
  @Test
  public void testForDataSetDefinitionOnother() throws EvaluationException {
    EvaluatedCohort evaluatedCohort =
        evaluateCohortDefinition(genericCohortQueries.getBaseCohort());
    DataSetDefinition dataSetDefinition =
        this.intensiveMonitoringDataSet.constructIntensiveMonitoringDataSet();
    EvaluationContext context = new EvaluationContext();
    context.addParameterValue("revisionEndDate", getEndDate());
    context.addParameterValue("location", getLocation());
    context.setBaseCohort(evaluatedCohort);
    DataSetDefinitionService dataSetDefinitionService =
        Context.getService(DataSetDefinitionService.class);
    DataSet dataset = dataSetDefinitionService.evaluate(dataSetDefinition, context);
    Iterator iterator = dataset.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }



  //TEST FOR DATASETDEFINITION
  @Test
  public void testForDataSetDefinition() throws EvaluationException {

    DataSetDefinition dataSetDefinition = tptInitiationNewDataSet.constructDataSet();

    EvaluationContext context = new EvaluationContext();
    context.addParameterValue("endDate", getEndDate());
    context.addParameterValue("startDate", getStartDate());
    context.addParameterValue("location", getLocation());

    context.setBaseCohort(new Cohort("", "", new Integer[] {507}));

    DataSetDefinitionService dataSetDefinitionService =
        Context.getService(DataSetDefinitionService.class);

    DataSet dataset = dataSetDefinitionService.evaluate(dataSetDefinition, context);

    Iterator iterator = dataset.iterator();

    while (iterator.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) iterator.next();
      Map<DataSetColumn, Object> map = dataSetRow.getColumnValues();
      for (Object o : map.entrySet()) {
        System.out.println(o);
      }
    }
  }

  @Override
  protected Date getStartDate() {
    return DateUtil.getDateTime(2021, 9, 20);
  }

  @Override
  protected Date getEndDate() {
    Date date = DateUtil.getDateTime(2022, 9, 20);
    return date;
  }

  protected Date getRevisionEndDate() {
    Date data = DateUtil.getDateTime(2022, 9, 20);
    return data;
  }

  protected Date evaluationPeriodStartDate() {
    Date data = DateUtil.getDateTime(2022, 3, 21);
    return data;
  }

  protected Date evaluationPeriodEndDate() {
    Date data = DateUtil.getDateTime(2022, 6, 20);
    return data;
  }

  @Override
  protected Location getLocation() {
    return Context.getLocationService().getLocation(399); // LOCATION
    // return Context.getLocationService().getLocation(158); // echomaster
    // return Context.getLocationService().getLocation(282); // jhpiegomaster
    // return Context.getLocationService().getLocation(158); // echomaster
    // return Context.getLocationService().getLocation(214); // egpafmaster
    // return Context.getLocationService().getLocation(244); // arielmaster
    // return Context.getLocationService().getLocation(400); // fgh
    // return Context.getLocationService().getLocation(271); // icapmaster
    // return Context.getLocationService().getLocation(208); // ccsmaster
    // return Context.getLocationService().getLocation(234); // echo
  }

  @Override
  protected String username() {
    return "admin";
  }

  @Override
  protected String password() {
    return "eSaude123";
  }

  @Override
  protected void setParameters(
      Date startDate, Date endDate, Location location, EvaluationContext context) {
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();

    calendar1.setTime(endDate);
    calendar2.setTime(endDate);
    calendar1.add(Calendar.MONTH, -1);
    calendar2.add(Calendar.MONTH, -4);

    context.addParameterValue("startDate", startDate);
    // context.addParameterValue("onOrBefore", calendar2.getTime());
    context.addParameterValue("onOrBefore", endDate);
    context.addParameterValue("endDate", endDate);
    context.addParameterValue("location", location);
    context.addParameterValue("revisionEndDate", getRevisionEndDate());
    context.addParameterValue("evaluationPeriodStartDate", evaluationPeriodStartDate());
    context.addParameterValue("valuationPeriodEndDate", evaluationPeriodEndDate());
  }

  @Override
  public Properties getRuntimeProperties() {
    runtimeProperties = new Properties();
    runtimeProperties.setProperty("connection.username", dbUsername);
    runtimeProperties.setProperty("connection.password", dbPassword);
    runtimeProperties.setProperty("connection.url", dbConnectionUrl);
    runtimeProperties.setProperty("hibernate.connection.url", dbConnectionUrl);
    runtimeProperties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    runtimeProperties.setProperty("hibernate.connection.username", dbUsername);
    runtimeProperties.setProperty("hibernate.connection.password", dbPassword);
    runtimeProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    return runtimeProperties;
  }
}
