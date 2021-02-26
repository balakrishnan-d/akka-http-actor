import java.io._
import java.util.UUID

object FileGenerator extends App {
  val printW = new PrintWriter(new File("/Users/bdoraisa/Downloads/AgentTemplate.csv"))
  printW.write("PrimaryRow,EmailID,LoginID,FirstName,LastName,ChatAcceptanceThreshold,BusinessUnit,Locations,RoleType,Role,Group,Team,CustomPermissions,CustomAttributeKey,CustomAttributeValue" + "\n")
  for ( i <- 1 to 3000) {
    val agentID = UUID.randomUUID().toString
    printW.write(s"yes,testCCUser$i@mailinator.com,$agentID,test,CCUser$i,,,,CustomerCare,Agent,,,,,," + "\n")
  }

  printW.close()
}
