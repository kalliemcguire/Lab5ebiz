package database;

import domain.*;

import java.util.*;
import javax.persistence.*;

public class PayrollDA {
    private static ArrayList<Payroll> payrollRecords = new ArrayList<Payroll>(5);
    
    public static void add(Payroll p) {
        EntityManager em = PayrollSystemDA.getEmFactory().createEntityManager();
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            em.persist(p);
            System.out.println("Payroll to add " + p);
            transact.commit();
        } finally {
            em.close();
        }
    }
    //method should take userID and date as arguments, display payroll records for that employee on that date
    public static ArrayList<Payroll> getPayrollRecords(int employeeID, Date date) {
        payrollRecords.clear();
                
        EntityManager em = PayrollSystemDA.getEmFactory().createEntityManager();
		String qString = "SELECT pr FROM Payroll pr" +
                       " WHERE pr.employeeID = :empID AND" +
                       " pr.date = :date";

		TypedQuery<Payroll> q = em.createQuery(qString, Payroll.class);
                q.setParameter("empID", employeeID);
                q.setParameter("date", date);
	
		List<Payroll> pRoll;
		try{
			pRoll = q.getResultList();
			payrollRecords = new ArrayList(pRoll);
		}
		finally {em.close();}

		return payrollRecords;
    }
}