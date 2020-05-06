import { shallowMount } from '@vue/test-utils';
import SemanticEnrichment from '@/views/semantic-enrichment/SemanticEnrichment'

describe('SemanticEnrichment.vue', () => {
  it('should render Create User Button', () => {
    const wrapper = shallowMount(SemanticEnrichment);
    const contentButton = wrapper.find('button');
    expect(contentButton.text()).toEqual('Save metadata');
  })
})
