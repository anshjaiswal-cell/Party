import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

// Validate required parameters
if (!partyId) {
    ec.message.addError("partyId is required")
    return
}
if (!firstName) {
    ec.message.addError("firstName is required")
    return
}
if (!lastName) {
    ec.message.addError("lastName is required")
    return
}

// Verify that a Party record exists for the given partyId
EntityValue party = ec.entity.find("party.Party").condition("partyId", partyId).one()
if (!party) {
    ec.message.addError("Party with ID [${partyId}] does not exist. Please create a Party record first.")
    return
}

// Create the Person record
Map personMap = [partyId: partyId, firstName: firstName, lastName: lastName]
if (dateOfBirth) personMap.dateOfBirth = dateOfBirth

ec.entity.makeValue("party.Person").setAll(personMap).create()

// Return success message
result = "Person ${firstName} ${lastName} created successfully!"
