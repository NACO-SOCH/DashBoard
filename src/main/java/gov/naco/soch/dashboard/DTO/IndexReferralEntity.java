package gov.naco.soch.dashboard.DTO;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IndexReferralEntity {
    
     @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        private String firstName;
        
        private String middleName;
        
        private String lastName;
        
        private Long sex;
        
        private LocalDate birthDate;
        
        private String age;
        
        private String phoneNo;
        
        private String addressLine1;
        
        private String addressLine2;
        
        private Long stateId;
        
        private Long districtId;
        
        private Long indexTypeId;
        
        private Long beneficiaryId;
        
        private Long indexBeneficiaryId;
        
        private String pincode;
        
        private Long facilityId;

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public Long getSex() {
            return sex;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public String getAge() {
            return age;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public Long getStateId() {
            return stateId;
        }

        public Long getDistrictId() {
            return districtId;
        }

        public Long getIndexTypeId() {
            return indexTypeId;
        }

        public Long getBeneficiaryId() {
            return beneficiaryId;
        }

        public String getPincode() {
            return pincode;
        }

        public Long getFacilityId() {
            return facilityId;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setSex(Long sex) {
            this.sex = sex;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public void setStateId(Long stateId) {
            this.stateId = stateId;
        }

        public void setDistrictId(Long districtId) {
            this.districtId = districtId;
        }

        public void setIndexTypeId(Long indexTypeId) {
            this.indexTypeId = indexTypeId;
        }

        public void setBeneficiaryId(Long beneficiaryId) {
            this.beneficiaryId = beneficiaryId;
        }
        
        
        public Long getIndexBeneficiaryId() {
			return indexBeneficiaryId;
		}

		public void setIndexBeneficiaryId(Long indexBeneficiaryId) {
			this.indexBeneficiaryId = indexBeneficiaryId;
		}

		public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public void setFacilityId(Long facilityId) {
            this.facilityId = facilityId;
        }

        
        
}
